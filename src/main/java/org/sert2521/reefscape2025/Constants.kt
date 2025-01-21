package org.sert2521.reefscape2025

import edu.wpi.first.math.MatBuilder.fill
import edu.wpi.first.math.Matrix
import edu.wpi.first.math.Nat
import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.numbers.N1
import edu.wpi.first.math.numbers.N3

object ConfigConstants {

}

object PhysicalConstants {

    const val WRIST_ENCODER_OFFSET = 0.0
    const val WRIST_ENCODER_MULTIPLIER = 0.0
    const val WRIST_ENCODER_TRANSFORM = 0.0

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
    const val WRIST_CORAL_OUTTAKE_SPEED = -0.0 //Should be negative
    const val WRIST_ALGAE_INTAKE_SPEED = 0.0
    const val WRIST_ALGAE_OUTTAKE_SPEED = -0.0 //Should be negative
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

    var swerveModuleData = listOf(

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
}

class SwerveModuleData(val position: Translation2d, val powerMotorID: Int, val angleMotorID: Int, val angleEncoderID: Int, val angleOffset: Double, val inverted: Boolean){}


object VisionConstants {

    val defaultVisionDeviations: Matrix<N3, N1> = fill(Nat.N3(), Nat.N1(), 0.4, 0.4, 3.0)
    val alignVisionDeviations: Matrix<N3, N1> = fill(Nat.N3(), Nat.N1(),0.4, 0.4, 3.0)

}