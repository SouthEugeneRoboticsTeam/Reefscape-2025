package org.sert2521.reefscape2025

import com.pathplanner.lib.config.PIDConstants
import edu.wpi.first.math.filter.SlewRateLimiter
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.units.Units
import edu.wpi.first.units.Units.Pounds
import org.sert2521.reefscape2025.PhysicalConstants.HALF_SIDE_LENGTH
import kotlin.math.PI

object ConfigConstants { // Drivetrain configs are now in DrivetrainConstants

    const val WRIST_STOW_SETPOINT = 0.0
    const val WRIST_GROUND_SETPOINT = 0.0
    const val WRIST_L1_SETPOINT = 0.0
    const val WRIST_ALGAE_SETPOINT = 0.0

}

object PhysicalConstants {

    const val WRIST_ENCODER_OFFSET = 0.0
    const val WRIST_ENCODER_MULTIPLIER = 0.0
    const val WRIST_ENCODER_TRANSFORM = 0.0

    const val HALF_SIDE_LENGTH = 0.0
    const val DRIVE_BASE_RADIUS = 0.0

    val robotMass = Pounds.of(115.0)
    val momentOfInertia = Units.KilogramSquareMeters.of(0.0)

}

object ElectronicIDs {

    const val WRIST_ENCODER_ID = -1
    const val WRIST_MOTOR_ID = -1
    const val GROUND_INTAKE_ROLLER_ID = -1

    const val DISPENSER_MOTOR_ID = -1
    const val BEAMBREAK_ID = -1

}

object TunedConstants {

    const val DISPENSER_INTAKE_SPEED = 0.0
    const val DISPENSER_RESET_SPEED = 0.0
    const val DISPENSER_OUTTAKE_SPEED = 0.0

    const val WRIST_CORAL_INTAKE_SPEED = 0.0
    const val WRIST_CORAL_OUTTAKE_SPEED = 0.0 //Should be positive
    const val WRIST_ALGAE_INTAKE_SPEED = 0.0
    const val WRIST_ALGAE_OUTTAKE_SPEED = 0.0 //Should be positive
}

object CurrentLimits {

    const val WRIST_CURRENT_LIMIT = 30
    const val WRIST_ROLLER_CURRENT_LIMIT = 30
    const val DISPENSER_CURRENT_LIMIT = 30

}

object PIDFFConstants {

    const val WRIST_P = 0.0
    const val WRIST_I = 0.0
    const val WRIST_D = 0.0

    const val WRIST_S = 0.0
    const val WRIST_G = 0.0
    const val WRIST_V = 0.0
    const val WRIST_A = 0.0

}

object RuntimeConstants {

    var wristSetpoint = 0.0

}

object DrivetrainConstants {

    const val DRIVE_SPEED = 0.0
    const val DRIVE_SECONDARY_SPEED = 0.0

    const val TURN_SPEED = 0.0

    val swerveModuleData = listOf(
        SwerveModuleData(Translation2d(HALF_SIDE_LENGTH, -HALF_SIDE_LENGTH), 5, 7, 16, -0.355-1.61-1.56+ PI /2, false), //Back Left
        SwerveModuleData(Translation2d(-HALF_SIDE_LENGTH, -HALF_SIDE_LENGTH), 1, 2, 15, -0.138-1.57-1.54+ PI /2, false), //Back Right
        SwerveModuleData(Translation2d(HALF_SIDE_LENGTH, HALF_SIDE_LENGTH), 16, 15, 14, 2.41-1.612-1.58+ PI /2, false), //Front Left
        SwerveModuleData(Translation2d(-HALF_SIDE_LENGTH, HALF_SIDE_LENGTH), 3, 12, 13, 0.059-1.568-1.575+ PI /2, false) //Front Right
    )

    const val DRIVE_MOTOR_INVERTED = false
    const val ANGLE_MOTOR_INVERTED = false

    const val DRIVE_CURRENT_LIMIT = 0
    const val ANGLE_CURRENT_LIMIT = 0

    const val ANGLE_ENCODER_MULTIPLY = 1.0
    const val DRIVE_ENCODER_MULTIPLY_POSITION = 1.0
    const val DRIVE_ENCODER_MULTIPLY_VELOCITY = DRIVE_ENCODER_MULTIPLY_POSITION / 60

    const val DRIVE_P = 0.0
    const val DRIVE_I = 0.0
    const val DRIVE_D = 0.0

    const val ANGLE_P = 0.0
    const val ANGLE_I = 0.0
    const val ANGLE_D = 0.0

    const val DRIVE_DEADBAND = 0.0
    const val ROTATION_DEADBAND = 0.0

    // Auto Stuff I Guess
    val autoTranslationPID = PIDConstants(0.0, 0.0, 0.0)
    val autoRotationPID = PIDConstants(0.0, 0.0, 0.0)

    const val WHEEL_COF = 1.54
    const val WHEEL_RADIUS_METERS = 0.0508

    const val MAX_ANGULAR_SPEED  = 4.571
    const val MAX_ROT_SPEED = 0.0 // TODO: Change this

    val driveMotorGearbox = DCMotor.getNEO(1).withReduction(6.75)

    const val DRIVE_AUTO_CURRENT_LIMIT = 0

    const val VISION_AIM_P = 0.0
    const val VISION_RANGE_P = 0.0

    val VISION_X_SPEED_LIMIT = SlewRateLimiter(3.0)
    val VISION_Y_SPEED_LIMIT = SlewRateLimiter(3.0)
    val VISION_ROT_SPEED_LIMIT = SlewRateLimiter(3.0)

}

// Creates a class for the swerve modules in the drivetrain
class SwerveModuleData(val position: Translation2d, val driveMotorID: Int, val angleMotorID: Int, val angleEncoderID: Int, val angleOffset: Double, val inverted: Boolean){}