package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.Dispenser

class DispenserReset : Command() {
    var triggered = false

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {
        Dispenser.setSpeed(-TunedConstants.DISPENSER_RESET_SPEED)
    }

    override fun execute() {
        if (!triggered && Dispenser.getBeamBreak()) {
            triggered = true
            Dispenser.setSpeed(TunedConstants.DISPENSER_RESET_SPEED)
        }
    }

    override fun isFinished(): Boolean {
        return triggered && !Dispenser.getBeamBreak()
    }

    override fun end(interrupted: Boolean) {
        Dispenser.stop()
    }
}
