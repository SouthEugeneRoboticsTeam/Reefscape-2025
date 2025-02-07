package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.WristRollers
import org.sert2521.reefscape2025.subsystems.Wrist

class WristIntake(): Command() {

    init { addRequirements(WristRollers) }

    override fun initialize() { WristRollers.setSpeed(TunedConstants.WRIST_INTAKE_SPEED) }

    override fun execute() {}

    override fun end(interrupted: Boolean) { WristRollers.stop() }

}