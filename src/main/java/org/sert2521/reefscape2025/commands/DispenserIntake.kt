package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.Dispenser

class DispenserIntake : Command() {
    var triggered = false


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {
        if (Dispenser.getBeamBreak()) {
            triggered = true
        }
        Dispenser.setSpeed(TunedConstants.DISPENSER_INTAKE_SPEED)
    }

    override fun execute() {
        if (!triggered && Dispenser.getBeamBreak()) {
            triggered = true
            Dispenser.setSpeed(TunedConstants.DISPENSER_INTAKE_SPEED)
        }
    }

    override fun isFinished(): Boolean {
        return triggered && !Dispenser.getBeamBreak()
    }

    override fun end(interrupted: Boolean) {
        Dispenser.stop()
    }
}
