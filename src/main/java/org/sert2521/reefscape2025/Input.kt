package org.sert2521.reefscape2025

import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton

object Input {
    private val driverController = XboxController(0)
    private val gunnerController = Joystick(1)

}