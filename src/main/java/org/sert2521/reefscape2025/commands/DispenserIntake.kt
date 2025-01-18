package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.subsystems.Dispenser

class DispenserIntake : Command() {


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements()
    }

    override fun initialize() {
        Dispenser.setSpeed(0.732)
    }

    override fun execute() {

    }

    override fun isFinished(): Boolean {
        return Dispenser.getBeamBreak()
    }

    override fun end(interrupted: Boolean) {
        Dispenser.stop()
    }
}
