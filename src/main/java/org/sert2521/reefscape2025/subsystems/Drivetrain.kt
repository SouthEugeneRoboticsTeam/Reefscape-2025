package org.sert2521.reefscape2025.subsystems

import com.ctre.phoenix6.hardware.CANcoder
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import com.studica.frc.AHRS
import edu.wpi.first.hal.simulation.AnalogGyroDataJNI.getAngle
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.*
import edu.wpi.first.math.util.Units
import edu.wpi.first.wpilibj.MotorSafety
import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.reefscape2025.DrivetrainConstants
import org.sert2521.reefscape2025.VisionConstants
import org.sert2521.reefscape2025.commands.JoystickDrive
import kotlin.math.*

abstract class SwerveModule(
    private val driveMotor: SparkMax,
    private val angleMotor: SparkMax,
    private val angleEncoder: CANcoder,
    private val angleOffset: Double,
    var state: SwerveModuleState,
    brakemode: Boolean) : MotorSafety() {

    var position: SwerveModulePosition
    private var goal = state
    private var reference = 0.0
    val inverted = false

    init{

        var driveConfig = SparkMaxConfig()
        var angleConfig = SparkMaxConfig()

        // Drive Motors
        driveConfig.inverted(DrivetrainConstants.DRIVE_MOTOR_INVERTED)
        driveConfig.idleMode(SparkBaseConfig.IdleMode.kBrake)
        driveConfig.smartCurrentLimit(DrivetrainConstants.DRIVE_CURRENT_LIMIT)

        driveConfig.closedLoop.p(DrivetrainConstants.DRIVE_P)
        driveConfig.closedLoop.i(DrivetrainConstants.DRIVE_I)
        driveConfig.closedLoop.d(DrivetrainConstants.DRIVE_D)

        driveConfig.encoder.positionConversionFactor(DrivetrainConstants.DRIVE_ENCODER_MULTIPLY_POSITION)
        driveConfig.encoder.velocityConversionFactor(DrivetrainConstants.DRIVE_ENCODER_MULTIPLY_VELOCITY)

        // Angle Motors
        angleConfig.inverted(DrivetrainConstants.ANGLE_MOTOR_INVERTED)
        angleConfig.idleMode(SparkBaseConfig.IdleMode.kBrake)
        angleConfig.smartCurrentLimit(DrivetrainConstants.ANGLE_CURRENT_LIMIT)

        angleConfig.closedLoop.p(DrivetrainConstants.ANGLE_P)
        angleConfig.closedLoop.i(DrivetrainConstants.ANGLE_I)
        angleConfig.closedLoop.d(DrivetrainConstants.ANGLE_D)

        angleConfig.closedLoop.positionWrappingEnabled(true)
        angleConfig.closedLoop.positionWrappingMinInput(-PI)
        angleConfig.closedLoop.positionWrappingMaxInput(PI)

        // Angle Encoders
        angleConfig.encoder.positionConversionFactor(DrivetrainConstants.ANGLE_ENCODER_MULTIPLY)
        angleConfig.encoder.velocityConversionFactor(DrivetrainConstants.ANGLE_ENCODER_MULTIPLY / 60)

        position = SwerveModulePosition(driveMotor.encoder.position, getAngle(0))
    }

    fun getAngle(): Rotation2d {
        if(inverted) {
            angleMotor.encoder.setPosition(angleEncoder.absolutePosition.valueAsDouble * DrivetrainConstants.ANGLE_ENCODER_MULTIPLY - angleOffset)
        }else{
            angleMotor.encoder.setPosition((-(angleEncoder.absolutePosition.valueAsDouble * DrivetrainConstants.ANGLE_ENCODER_MULTIPLY - angleOffset))
        }

        return Rotation2d((angleMotor.encoder.position+ PI).mod(2*PI)-PI)
    }

    // Run periodically
    fun updateState() {
        val angle = getAngle()
        state = SwerveModuleState(driveMotor.encoder.velocity, angle)
        position = SwerveModulePosition(driveMotor.encoder.position, angle)
    }

    fun set(wanted: SwerveModuleState) {
        val optimized = SwerveModuleState.optimize(state, getAngle()) //TODO: Fix this, I don't know what it should be instead
        val driveError = optimized.speedMetersPerSecond - driveMotor.encoder.velocity

        // In Marvin's code for this there is commented-out text about PIDFF stuff. However, since it's removed, I won't add it for now.

        goal = SwerveModuleState(optimized.speedMetersPerSecond, Rotation2d(optimized.angle.radians))
        reference = driveError.pow(2) * sign(driveError) + driveMotor.encoder.velocity
        angleMotor.closedLoopController.setReference(optimized.angle.radians, SparkBase.ControlType.kPosition)
    }

    fun getModuleGoal():SwerveModuleState {
        return SwerveModuleState(goal.speedMetersPerSecond, Rotation2d(goal.angle.radians))
    }

    fun setMotorMode(coast: Boolean, driveConfig:SparkMaxConfig, angleConfig:SparkMaxConfig) {
        if(coast) {
            driveConfig.idleMode(SparkBaseConfig.IdleMode.kCoast)
            angleConfig.idleMode(SparkBaseConfig.IdleMode.kCoast)
        } else {
            driveConfig.idleMode(SparkBaseConfig.IdleMode.kBrake)
            angleConfig.idleMode(SparkBaseConfig.IdleMode.kBrake)
        }

    }

    fun getModuleReference(): Double {
        return reference
    }

    fun getAmps() :Pair<Double, Double> {
        return Pair(driveMotor.outputCurrent, angleMotor.outputCurrent)
    }

    fun setCurrentLimit(amps:Int, driveConfig:SparkMaxConfig) {
        driveConfig.smartCurrentLimit(amps)
    }

    override fun stop() {
        driveMotor.stopMotor()
        angleMotor.stopMotor()
    }

    fun getEncoderHealth(): Double {
        return angleEncoder.magnetHealth.valueAsDouble
    }
}

object Drivetrain : SubsystemBase() {

    private val imu = AHRS()

    private val kinematics: SwerveDriveKinematics
    private var modules: Array<SwerveModule>
    private var odometry: SwerveDriveOdometry
    private val poseEstimator: SwerveDrivePoseEstimator

    private var pose = Pose2d()
    private var visionPose = Pose2d()

    private var prevPose = Pose2d()
    private var prevTime = Timer.getFPGATimestamp()

    var deltaPose = Pose2d()
        private set

    init {

        val modulePositions = mutableListOf<Translation2d>()
        val moduleList = mutableListOf<SwerveModule>()

        for (moduleData in DrivetrainConstants.swerveModuleData) {
            val driveMotor = SparkMax(moduleData.driveMotorID, SparkLowLevel.MotorType.kBrushless)
            val angleMotor = SparkMax(moduleData.angleMotorID, SparkLowLevel.MotorType.kBrushless)

            modulePositions.add(moduleData.position)
            val module = createModule(driveMotor, angleMotor, moduleData)
            module.isSafetyEnabled = true
            moduleList.add(module)
        }

        modules = moduleList.toTypedArray()

        val positions = mutableListOf<SwerveModulePosition>()

        for (module in modules) {
            module.updateState()
            positions.add(module.position)
        }

        val positionsArray = positions.toTypedArray()

        kinematics = SwerveDriveKinematics(*modulePositions.toTypedArray())
        odometry = SwerveDriveOdometry(kinematics, -imu.rotation2d, positionsArray, Pose2d())
        poseEstimator = SwerveDrivePoseEstimator(kinematics, -imu.rotation2d, positionsArray, Pose2d())

        Drivetrain.defaultCommand = JoystickDrive(true)

    }

    // "Fix this nonsense" -Whoever made the original code
    fun getPose(): Pose2d {

        return Pose2d(pose.y, pose.x, -pose.rotation)

    }

    fun getVisionPose(): Pose2d {

        return Pose2d(visionPose.x, visionPose.y, -visionPose.rotation)

    }

    // "Fix this nonsense" -Whoever made the original code
    private fun createModule(powerMotor: SparkMax, angleMotor:SparkMax, moduleData: SwerveModuleData): SwerveModule {

        return SwerveModule(
            powerMotor,
            angleMotor,
            CANcoder(moduleData.angleEncoderID),
            moduleData.angleOffset,
            SwerveModuleState(),
            true
        )

    }

    override fun periodic() {

        val positions = mutableListOf<SwerveModulePosition>()

        for (module in modules) {
            module.updateState()
            positions.add(module.position)
        }

        val positionsArray = positions.toTypedArray()

        pose = odometry.update(-imu.rotation2d, positionsArray)

        // Vision stuff I think?

        val currTime = Timer.getFPGATimestamp()
        val deltaTime = currTime - prevTime

        deltaPose = Pose2d((pose.y - prevPose.y) / deltaTime, (pose.x - prevPose.x) / deltaTime, -(pose.rotation - prevPose.rotation) / deltaTime)

        prevPose = pose
        prevTime = currTime

    }

    fun setNewPose(newPose:Pose2d) {

        pose = Pose2d(newPose.y, newPose.x, -newPose.rotation)

        val positions = mutableListOf<SwerveModulePosition>()

        for (module in modules) {
            module.updateState()
            positions.add(module.position)
        }

        val positionsArray = positions.toTypedArray()

        odometry.resetPosition(-imu.rotation2d, positionsArray, pose)

    }

    fun setNewVisionPose{

        val positions = mutableListOf<SwerveModulePosition>()

        for (module in modules) {
            module.updateState()
            positions.add(module.position)
        }

        val positionsArray = positions.toTypedArray()

        poseEstimator.resetPosition(-imu.rotation2d, positionsArray, newPose)

    }

    fun setVisionStandardDeviations() {

        poseEstimator.setVisionMeasurementStdDevs(VisionConstants.defaultVisionDeviations)

    }

    fun setVisionAlignDeviations() {

        poseEstimator.setVisionMeasurementStdDevs(VisionConstants.alignVisionDeviations) }

    fun getRelativeSpeeds(): ChassisSpeeds { return kinematics.toChassisSpeeds(modules[0].state, modules[1].state, modules[2].state, modules[3].state) }

    fun getAbsoluteSpeeds(): ChassisSpeeds { return ChassisSpeeds.fromRobotRelativeSpeeds(getRelativeSpeeds(), getPose().rotation) }

    fun getAccelerationSquared():Double { return (imu.worldLinearAccelY.pow(2) + imu.worldLinearAccelX.pow(2)).toDouble() }

    private fun feed() {

        for (module in modules) {
            module.feed()
        }

    }

    fun drive(chassisSpeeds: ChassisSpeeds) {

        val wantedStates = kinematics.toSwerveModuleStates(ChassisSpeeds(chassisSpeeds.vyMetersPerSecond, chassisSpeeds.vxMetersPerSecond, -chassisSpeeds.omegaRadiansPerSecond))

        for (i in wantedStates.indices) {
            modules[i].set(wantedStates[i])
        }

        feed()

    }

    fun getTiltDirection(): Translation2d {

        val unNormalized = Translation2d(atan(Units.degreesToRadians(imu.roll.toDouble())), atan(Units.degreesToRadians(imu.pitch.toDouble())))
        val norm = unNormalized.norm

        if (norm == 0) {
            return unNormalized
        }

        return unNormalized / norm

    }

    fun getTilt(): Double { return atan(sqrt(tan(Units.degreesToRadians(imu.pitch.toDouble())).pow(2) + tan(Units.degreesToRadians(imu.roll.toDouble())).pow(2))) }

    fun getRoll(): Double {

        return Units.degreesToRadians(imu.roll.toDouble())

    }

    fun getGoals(): Array<SwerveModuleState> { return arrayOf(modules[0].getModuleGoal(), modules[1].getModuleGoal(), modules[2].getModuleGoal(), modules[3].getModuleGoal()) }

    fun getReferences(): Array<Double> { return arrayOf(modules[0].getModuleReference(), modules[1].getModuleReference(), modules[2].getModuleReference(), modules[3].getModuleReference()) }

    fun getAmps(): Array<Pair<Double, Double>>{ return arrayOf(modules[0].getAmps(), modules[1].getAmps(), modules[2].getAmps(), modules[3].getAmps()) }

    fun getDraw(): Double{ return modules[0].getAmps().first+modules[1].getAmps().first+modules[2].getAmps().first+modules[3].getAmps().first }

    fun getStates(): Array<SwerveModuleState>{ return arrayOf(modules[0].state, modules[1].state, modules[2].state, modules[3].state) }

    fun getHealth(module: Int): Double { return modules[module].getEncoderHealth() }

    fun setMode(coast: Boolean) {

        for (module in modules) {
            module.setMotorMode(coast)
        }

    }

    // Something about climbing, probably not the same in this game?


    fun setCurrentLimit(amps: Int) {

        for (module in modules) {
            module.setCurrentLimit(amps)
        }

    }

    fun stop() {

        for (module in modules) {
            module.stop()
        }

        feed()

    }


}