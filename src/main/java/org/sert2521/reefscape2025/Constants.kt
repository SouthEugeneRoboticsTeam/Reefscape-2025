package org.sert2521.reefscape2025

import edu.wpi.first.math.trajectory.TrapezoidProfile

object ConfigConstants {

}

object PhysicalConstants {

}

object ElectronicIDs {
    const val ELEVATOR_MOTOR_ONE = -1
    const val ELEVATOR_MOTOR_TWO = -1
    const val TOF_SENSOR = -1
}

object TunedConstants {
    const val ELEVATOR_P = 0.0
    const val ELEVATOR_I = 0.0
    const val ELEVATOR_D = 0.0

    const val ELEVATOR_S = 0.0
    const val ELEVATOR_V = 0.0
    const val ELEVATOR_G = 0.0

    val ELEVATOR_TRAPEZOIDAL_CONSTRAINTS = TrapezoidProfile.Constraints(0.0,0.0)
}

object RuntimeConstants {

}