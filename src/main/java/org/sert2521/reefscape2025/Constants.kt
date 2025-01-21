package org.sert2521.reefscape2025

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

}

object TunedConstants {

    const val WRIST_CORAL_INTAKE_SPEED = 0.0
    const val WRIST_CORAL_OUTTAKE_SPEED = 0.0 //Should be negative
    const val WRIST_ALGAE_INTAKE_SPEED = 0.0
    const val WRIST_ALGAE_OUTTAKE_SPEED = 0.0 //Should be negative

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

object CurrentLimits {

    const val WRIST_CURRENT_LIMIT = 30
    const val WRIST_ROLLER_CURRENT_LIMIT = 30

}