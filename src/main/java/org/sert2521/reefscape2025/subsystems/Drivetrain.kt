package org.sert2521.reefscape2025.subsystems

import edu.wpi.first.math.VecBuilder
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj.Filesystem
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.reefscape2025.DrivetrainConstants.MAX_DRIVE_SPEED
import limelightlib.LimelightHelpers
import org.sert2521.bunnybots2024.commands.JoystickDrive
import swervelib.math.SwerveMath
import swervelib.parser.SwerveParser
import swervelib.telemetry.SwerveDriveTelemetry
import java.io.File

object Drivetrain : SubsystemBase() {
    private val swerveDir = File(Filesystem.getDeployDirectory(), "swerve")
    val swerve = SwerveParser(swerveDir).createSwerveDrive(MAX_DRIVE_SPEED)


    val hasTarget = LimelightHelpers.getTV("")

    init {

        SwerveDriveTelemetry.verbosity = SwerveDriveTelemetry.TelemetryVerbosity.NONE

        this.defaultCommand = JoystickDrive()
    }

    fun driveRobotOriented(chassisSpeeds: ChassisSpeeds){
        swerve.drive(chassisSpeeds)
    }

    fun driveFieldOriented(fieldSpeeds: ChassisSpeeds){
        swerve.driveFieldOriented(fieldSpeeds)
    }

    fun driveHeadingSetpoint(inputX:Double, inputY:Double, heading:Rotation2d){
        swerve.driveFieldOriented(
            swerve.swerveController.getTargetSpeeds(
                inputX,
                inputY,
                heading.sin,
                heading.cos,
                swerve.yaw.radians,
                swerve.maximumVelocity
            )
        )
    }

    fun getTx(): Double{
        return LimelightHelpers.getTX("")
    }

    fun getTy(): Double{
        return LimelightHelpers.getTY("")
    }

    fun updateVision(){
        //example code LL gave us
        //probably correct
        var rejectUpdate = false
        LimelightHelpers.SetRobotOrientation(
            "limelight",
            swerve.yaw.degrees,
            swerve.gyro.rate,
            swerve.pitch.degrees,
            0.0,
            swerve.roll.degrees,
            0.0
        )
        val mt2 = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2("limelight")
        if (swerve.gyro.rate > 720 || mt2.tagCount == 0){
            rejectUpdate = true
        }
        if (!rejectUpdate){
            swerve.setVisionMeasurementStdDevs(
                VecBuilder.fill(0.7, 0.7, 999999999.9))

            swerve.addVisionMeasurement(mt2.pose, mt2.timestampSeconds)
        }
    }

    fun configureVision(){

    }

    fun getPose():Pose2d{
        return swerve.pose
    }

    fun setPose(newPose:Pose2d){
        swerve.resetOdometry(newPose)
    }

    fun getRelativeSpeeds():ChassisSpeeds{
        return swerve.robotVelocity
    }

    fun stop(){
        swerve.drive(ChassisSpeeds(0.0, 0.0, 0.0))
    }

    fun getMaxSpeed():Double{
        return swerve.maximumVelocity
    }


}