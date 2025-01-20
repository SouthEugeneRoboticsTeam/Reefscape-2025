package org.sert2521.reefscape2025.commands

import edu.wpi.first.math.controller.ArmFeedforward
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.RuntimeConstants
import org.sert2521.reefscape2025.PIDFFConstants
import org.sert2521.reefscape2025.subsystems.WristRollers
import org.sert2521.reefscape2025.subsystems.Wrist

class SetWrist: Command() {

    private val pid = PIDController(PIDFFConstants.WRIST_P, PIDFFConstants.WRIST_I, PIDFFConstants.WRIST_D)
    private val feedForward = ArmFeedforward(PIDFFConstants.WRIST_S, PIDFFConstants.WRIST_G, PIDFFConstants.WRIST_V, PIDFFConstants.WRIST_A)

    private var angle = Wrist.getRadians()

    init { addRequirements(Wrist, WristRollers) }

    override fun initialize() {}

    override fun execute() {

        angle = Wrist.getRadians()
        Wrist.setVoltage(pid.calculate(angle, RuntimeConstants.wristSetpoint) + feedForward.calculate(angle, 0.0))

    }

    override fun isFinished(): Boolean { return false }

    override fun end(interrupted: Boolean) {}

}