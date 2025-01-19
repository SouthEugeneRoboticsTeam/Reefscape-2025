package org.sert2521.reefscape2025

import edu.wpi.first.math.MatBuilder.fill
import edu.wpi.first.math.Matrix
import edu.wpi.first.math.Nat
import edu.wpi.first.math.numbers.N1
import edu.wpi.first.math.numbers.N3

object ConfigConstants {

}

object PhysicalConstants {

}

object ElectronicIDs {

}

object TunedConstants {

}

object RuntimeConstants {

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

object VisionConstants {

    val defaultVisionDeviations: Matrix<N3, N1> = fill(Nat.N3(), Nat.N1(), 0.4, 0.4, 3.0)
    val alignVisionDeviations: Matrix<N3, N1> = fill(Nat.N3(), Nat.N1(),0.4, 0.4, 3.0)

}