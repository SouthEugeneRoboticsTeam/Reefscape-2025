package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.Dispenser

class DispenserIntake : Command() {
    var triggered = 0

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {
        Dispenser.setSpeed(TunedConstants.DISPENSER_SPEED)
    }

    override fun execute() {
        if (triggered == 0 && Dispenser.getBeamBreak()) {
            triggered = 1
            Dispenser.setSpeed(TunedConstants.DISPENSER_SPEED)
        }
        if (triggered == 1 && !Dispenser.getBeamBreak()) {
            triggered = 2
            Dispenser.stop()
        }
    }

    override fun isFinished(): Boolean {
        return triggered == 2
    }

    override fun end(interrupted: Boolean) {
        Dispenser.stop()
    }
}
