package org.sert2521.reefscape2025.commands.ground_intake

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.SetpointConstants
import org.sert2521.reefscape2025.subsystems.WristRollers

class GroundOuttake(): Command() {

    init { addRequirements(WristRollers) }

    override fun initialize() { WristRollers.setSpeed(-SetpointConstants.WRIST_OUTTAKE_SPEED) }

    override fun execute() {}

    override fun end(interrupted: Boolean) { WristRollers.stop() }

}