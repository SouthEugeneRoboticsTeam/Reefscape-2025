package org.sert2521.reefscape2025

import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton

object Input {
    private val driverController = XboxController(0)
    private val gunnerController = Joystick(1)

    var rotationOffset = Rotation2d(0.0)

    fun getJoystickX():Double{ return -driverController.leftX }

    fun getJoystickY():Double{ return -driverController.leftY }

    fun getJoystickZ():Double { return -driverController.rightX }

    fun getRotOffset(): Rotation2d { return rotationOffset }

    fun getRotY():Double{ return driverController.rightY }
}