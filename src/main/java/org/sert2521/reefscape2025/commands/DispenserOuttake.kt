package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.Dispenser

class DispenserOuttake : Command() {

    init { addRequirements(Dispenser) }

    override fun initialize() { Dispenser.setSpeed(TunedConstants.DISPENSER_OUTTAKE_SPEED) }

    override fun execute() {}

    override fun isFinished(): Boolean { return false }

    override fun end(interrupted: Boolean) { Dispenser.stop() }

}
