package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.Dispenser

class DispenserReset : Command() {

    var triggered = false

    init { addRequirements(Dispenser) }

    override fun initialize() {
        if (Dispenser.getDispenserBeamBreak()) {
            triggered = true
            Dispenser.setSpeed(TunedConstants.DISPENSER_RESET_SPEED)
        } else {
            Dispenser.setSpeed(-TunedConstants.DISPENSER_RESET_SPEED)
        }

    }

    override fun execute() {

        if (!triggered && Dispenser.getDispenserBeamBreak()) {
            triggered = true
            Dispenser.setSpeed(TunedConstants.DISPENSER_RESET_SPEED)
        }

    }

    override fun isFinished(): Boolean { return triggered && !Dispenser.getDispenserBeamBreak() }

    override fun end(interrupted: Boolean) { Dispenser.stop() }

}
