package org.sert2521.bunnybots2024.commands

import edu.wpi.first.math.geometry.Translation2d
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.Input
import org.sert2521.reefscape2025.DrivetrainConstants
import org.sert2521.reefscape2025.subsystems.Drivetrain
import kotlin.math.*

class JoystickDrive(private val fieldOriented:Boolean = true) : Command() {
    val joystickX = Input::getJoystickX
    val joystickY = Input::getJoystickY
    val joystickZ = Input::getJoystickZ

    val inputRotOffset = Input::getRotOffset



    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(Drivetrain)
    }

    override fun initialize() {

    }

    override fun execute() {
        val x = joystickX()
        val y = joystickY()



        var magnitude = Translation2d(x, y).norm
        val angle = atan2(y, x)


        //this math works just trust
        //it normalizes the magnitude from a square output range to a circular one
        //new magnitude is true to actual distance from the center
        val mult: Double =
            if (x == 0.0 || y == 0.0){
                1.0
            }else if (abs(x)>=abs(y)){
                abs(cos(angle))
            } else {
                abs(sin(angle))
            }

        magnitude *= mult

        //Do joystick curve
        magnitude = magnitude.pow(3)

        //reconstructs the x and y components from polar coordinates
        val newX = cos(angle)*magnitude
        val newY = sin(angle)*magnitude

        if (joystickX() == 0.0 && joystickY() == 0.0 && joystickZ() == 0.0){
            Drivetrain.stop()
        }
        else if (fieldOriented) {
            Drivetrain.driveRobotOriented(
                ChassisSpeeds.fromFieldRelativeSpeeds(
                    newY * DrivetrainConstants.DRIVE_SPEED,
                    newX * DrivetrainConstants.DRIVE_SPEED,
                    joystickZ().pow(3) * DrivetrainConstants.ROT_SPEED,
                    Drivetrain.getPose().rotation.minus(inputRotOffset())
                )
            )
        }
        else {
            Drivetrain.driveRobotOriented(
                ChassisSpeeds(
                    newY * DrivetrainConstants.DRIVE_SPEED,
                    newX * DrivetrainConstants.DRIVE_SPEED,
                    joystickZ().pow(3) * DrivetrainConstants.ROT_SPEED
                )
            )
        }
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end(interrupted: Boolean) {
        Drivetrain.stop()
    }
}
