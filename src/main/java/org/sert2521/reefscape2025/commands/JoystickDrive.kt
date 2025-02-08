package org.sert2521.reefscape2025.commands

import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.DrivetrainConstants
import org.sert2521.reefscape2025.Input
import org.sert2521.reefscape2025.subsystems.Drivetrain
import kotlin.math.*


class JoystickDrive(private val fieldOriented: Boolean = true): Command() {

    val joystickX = Input::getLeftJoystickX
    val joystickY = Input::getLeftJoystickY
    val joystickZ = Input::getRightJoystickX
    val inputRotOffset = Input::getRotOffset

    init {

        addRequirements(Drivetrain)

    }

    override fun initialize() {}

    override fun execute() {

        val x = joystickX()
        val y = joystickY()

        var magnitude = Translation2d(x, y).norm
        val angle = atan2(x, y)

        // This math (lines 38 to 51 was made by Benji
        // If it doesn't work blame them
        val mult: Double =
            if ((x == 0.0) || (y == 0.0)){
                1.0
            }else if (abs(x) >= abs(y)){
                abs(cos(angle))
            } else {
                abs(sin(angle))
            }

        magnitude *= mult
        magnitude = magnitude.pow(3)

        val newX = cos(angle) * magnitude
        val newY = sin(angle) * magnitude

        if ((joystickX() == 0.0) && (joystickY() == 0.0) && (joystickX() == 0.0)) {

            Drivetrain.stop()

        } else if (fieldOriented) {

            Drivetrain.drive(
                ChassisSpeeds.fromFieldRelativeSpeeds(
                    newY * DrivetrainConstants.DRIVE_SPEED,
                    newX * DrivetrainConstants.DRIVE_SPEED,
                    joystickZ().pow(3) * DrivetrainConstants.TURN_SPEED,
                    Drivetrain.getPose().rotation.minus(inputRotOffset())
                )
            )

        } else {

            Drivetrain.drive(
                ChassisSpeeds(
                    newY * DrivetrainConstants.DRIVE_SPEED,
                    newX * DrivetrainConstants.DRIVE_SPEED,
                    joystickZ().pow(3) * DrivetrainConstants.TURN_SPEED
                )
            )

        }

        /*override*/ fun isFinished(): Boolean { return false }

        /*override*/ fun end(interrupted: Boolean) { Drivetrain.stop() }

    }

}