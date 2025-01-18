package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.subsystems.Dispenser

class DispenserReverse : Command() {
    var triggered = 0

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {
        Dispenser.setSpeed(-1.0)
    }

    override fun execute() {
        if (triggered == 0 && Dispenser.getBeamBreak()) {
            triggered = 1
            Dispenser.setSpeed(-1.0)
        }
        if (triggered == 1 && !Dispenser.getBeamBreak()) {
            triggered = 2
            Dispenser.setSpeed(1.0)
        }
        if (triggered == 2 && Dispenser.getBeamBreak()) {
            triggered = 3
            Dispenser.setSpeed(0.0)
        }
    }

    override fun isFinished(): Boolean {
        return triggered == 3
    }

    override fun end(interrupted: Boolean) {
        Dispenser.stop()
    }
}
