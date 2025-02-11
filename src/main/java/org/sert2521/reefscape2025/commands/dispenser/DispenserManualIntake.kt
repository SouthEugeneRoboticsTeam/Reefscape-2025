package org.sert2521.reefscape2025.commands.dispenser

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.SetpointConstants
import org.sert2521.reefscape2025.subsystems.Dispenser

class DispenserManualIntake : Command() {

    init { addRequirements(Dispenser) }

    override fun execute() {

        Dispenser.setSpeed(SetpointConstants.DISPENSER_INTAKE_SPEED)

    }

    override fun end(interrupted: Boolean) { Dispenser.stop() }

}
