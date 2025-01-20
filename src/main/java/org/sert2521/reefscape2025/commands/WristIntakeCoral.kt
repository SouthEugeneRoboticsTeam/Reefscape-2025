package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.WristRollers
import org.sert2521.reefscape2025.subsystems.Wrist

class WristIntakeCoral(private val intakeSpeed: Double): Command() {

    init { addRequirements(WristRollers, Wrist) }

    override fun initialize() { WristRollers.setSpeed(TunedConstants.WRIST_CORAL_INTAKE_SPEED) }

    override fun execute() {}

    override fun end(interrupted: Boolean) { WristRollers.stop() }

}