package org.sert2521.reefscape2025.subsystems

import com.ctre.phoenix6.hardware.CANcoder
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.hal.simulation.AnalogGyroDataJNI.getAngle
import edu.wpi.first.math.kinematics.SwerveModulePosition
import edu.wpi.first.math.kinematics.SwerveModuleState
import edu.wpi.first.wpilibj.MotorSafety
import org.sert2521.reefscape2025.SwerveConstants
import swervelib.parser.SwerveDriveConfiguration
import kotlin.math.PI

class SwerveModule(
    private val driveMotor: SparkMax,
    private val angleMotor: SparkMax,
    private val angleEncoder: CANcoder,
    private val angleOffset: Double,
    var state: SwerveModuleState,
    brakemode: Boolean) : MotorSafety() {

        var position: SwerveModulePosition
        private var goal = state
        private var reference = 0.0

        init{

            val driveConfig = SparkMaxConfig()
            val angleConfig = SparkMaxConfig()

            //Drive Motors
            driveConfig.inverted(SwerveConstants.DRIVE_MOTOR_INVERTED)
            driveConfig.idleMode(SparkBaseConfig.IdleMode.kBrake)
            driveConfig.smartCurrentLimit(SwerveConstants.DRIVE_CURRENT_LIMIT)

            driveConfig.closedLoop.p = SwerveConstants.DRIVE_P
            driveConfig.closedLoop.i = SwerveConstants.DRIVE_I
            driveConfig.closedLoop.d = SwerveConstants.DRIVE_D

            driveConfig.encoder.positionConversionFactor(SwerveConstants.DRIVE_ENCODER_MULTIPLY_POSITION)
            driveConfig.encoder.velocityConversionFactor(SwerveConstants.DRIVE_ENCODER_MULTIPLY_VELOCITY)

            //Angle Motors
            angleConfig.inverted(SwerveConstants.ANGLE_MOTOR_INVERTED)
            angleConfig.idleMode(SparkBaseConfig.IdleMode.kBrake)
            angleConfig.smartCurrentLimit(SwerveConstants.ANGLE_CURRENT_LIMIT)

            angleConfig.closedLoop.p = SwerveConstants.ANGLE_P
            angleConfig.closedLoop.i = SwerveConstants.ANGLE_I
            angleConfig.closedLoop.d = SwerveConstants.ANGLE_D

            angleConfig.closedLoop.positionWrappingEnabled(true)
            angleConfig.closedLoop.positionWrappingMinInput(-PI)
            angleConfig.closedLoop.positionWrappingMaxInput(PI)

            //Angle Encoders
            angleConfig.encoder.positionConversionFactor(SwerveConstants.ANGLE_ENCODER_MULTIPLY)
            angleConfig.encoder.velocityConversionFactor(SwerveConstants.ANGLE_ENCODER_MULTIPLY / 60)

            position = SwerveModulePosition(driveMotor.encoder.position, getAngle(0))

        }

    }