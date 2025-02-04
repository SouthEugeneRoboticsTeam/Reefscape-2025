package org.sert2521.reefscape2025

import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController

object Input {
    private val driverController = XboxController(0)
    private val gunnerController = Joystick(1)

    var rotationOffset = Rotation2d(0.0)

    fun getLeftJoystickX() :Double{ return -driverController.leftX }

    fun getLeftJoystickY() :Double{ return -driverController.leftY }

    fun getRightJoystickX(): Double { return -driverController.rightX }

    fun getRightJoystickY(): Double { return -driverController.rightY }

    fun getAButton(): Boolean { return driverController.aButton }

    fun getRotOffset(): Rotation2d { return rotationOffset }

    fun getRotY() :Double{ return driverController.rightY }
}