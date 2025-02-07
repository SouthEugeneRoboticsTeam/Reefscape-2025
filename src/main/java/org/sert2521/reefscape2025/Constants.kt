package org.sert2521.reefscape2025

import com.pathplanner.lib.config.PIDConstants
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.system.plant.DCMotor
import edu.wpi.first.units.Units
import edu.wpi.first.units.Units.Pounds
import kotlin.math.PI
import edu.wpi.first.math.trajectory.TrapezoidProfile

object ConfigConstants {

    const val WRIST_ENCODER_MULTIPLIER = (2 *PI) / 1
    const val WRIST_ENCODER_TRANSFORM = 0.0

    const val HALF_SIDE_LENGTH = 0.5773 / 2.0
    const val DRIVE_BASE_RADIUS = 0.4082

    const val WRIST_STOW_SETPOINT = 0.0
    const val WRIST_GROUND_SETPOINT = 0.0
    const val WRIST_L1_SETPOINT = 0.0
    const val WRIST_ALGAE_SETPOINT = 0.0

    const val ELEVATOR_STOW_SETPOINT = 0.0
    const val ELEVATOR_L2_SETPOINT = 0.0
    const val ELEVATOR_L3_SETPOINT = 0.0
    const val ELEVATOR_L4_SETPOINT = 0.0

}

object ElectronicIDs {

    const val WRIST_ENCODER_ID = 18
    const val WRIST_MOTOR_ID = 16
    const val WRIST_ROLLER_ID = 17

    const val DISPENSER_MOTOR_ID = 15
    const val DISPENSER_BEAMBREAK_ID = 1
    const val RAMP_BEAMBREAK_ID = 2

    const val ELEVATOR_MOTOR_LEFT = 13
    const val ELEVATOR_MOTOR_RIGHT = 14
    const val TOF_SENSOR = 18

}

object TunedConstants {

    const val DISPENSER_INTAKE_SPEED = 0.0
    const val DISPENSER_RESET_SPEED = 0.0
    const val DISPENSER_OUTTAKE_SPEED = 0.0

    const val WRIST_OUTTAKE_SPEED = 0.0
    const val WRIST_INTAKE_SPEED = 0.0

}

object PIDFFConstants {

    const val WRIST_P = 0.0
    const val WRIST_I = 0.0
    const val WRIST_D = 0.0

    const val WRIST_S = 0.0
    const val WRIST_G = 0.0
    const val WRIST_V = 0.0
    const val WRIST_A = 0.0

    const val ELEVATOR_P = 0.0
    const val ELEVATOR_I = 0.0
    const val ELEVATOR_D = 0.0

    const val ELEVATOR_S = 0.0
    const val ELEVATOR_V = 0.0
    const val ELEVATOR_G = 0.0
    val ELEVATOR_TRAPEZOIDAL_CONSTRAINTS = TrapezoidProfile.Constraints(0.0,0.0)

}

object RuntimeConstants {

    var wristSetpoint = 0.0

}

object DrivetrainConstants {

    const val DRIVE_SPEED = 0.0
    const val DRIVE_SECONDARY_SPEED = 0.0

    const val TURN_SPEED = 0.0

    val swerveModuleData = listOf(
        SwerveModuleData(Translation2d(PhysicalConstants.HALF_SIDE_LENGTH, -PhysicalConstants.HALF_SIDE_LENGTH), 7, 11, 4, -0.355-1.61-1.56+ PI /2, false), //Back Left
        SwerveModuleData(Translation2d(-PhysicalConstants.HALF_SIDE_LENGTH, -PhysicalConstants.HALF_SIDE_LENGTH), 8, 12, 3, -0.138-1.57-1.54+ PI /2, false), //Back Right
        SwerveModuleData(Translation2d(PhysicalConstants.HALF_SIDE_LENGTH, PhysicalConstants.HALF_SIDE_LENGTH), 5, 9, 1, 2.41-1.612-1.58+ PI /2, false), //Front Left
        SwerveModuleData(Translation2d(-PhysicalConstants.HALF_SIDE_LENGTH, PhysicalConstants.HALF_SIDE_LENGTH), 6, 10, 2, 0.059-1.568-1.575+ PI /2, false) //Front Right
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

    const val MAX_SPEED_MPS  = 4.571

    val driveMotorGearbox = DCMotor.getNEO(1).withReduction(6.75)

    const val DRIVE_AUTO_CURRENT_LIMIT = 0

}

// Creates a class for the swerve modules in the drivetrain
class SwerveModuleData(val position: Translation2d, val driveMotorID: Int, val angleMotorID: Int, val angleEncoderID: Int, val angleOffset: Double, val inverted: Boolean){}

object PhysicalConstants {

    const val HALF_SIDE_LENGTH = 0.607
    val robotMass = Pounds.of(115.0)
    val momentOfInertia = Units.KilogramSquareMeters.of(0.0)

}
