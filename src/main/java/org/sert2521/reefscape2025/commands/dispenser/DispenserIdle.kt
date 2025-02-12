package org.sert2521.reefscape2025.commands.dispenser

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.SetpointConstants
import org.sert2521.reefscape2025.subsystems.Dispenser

class DispenserIdle : Command() {

    private val dispenser = Dispenser

    init { addRequirements(dispenser) }

    override fun initialize() {}

    override fun execute() {

        if (Dispenser.getRampBeamBreak() || Dispenser.getDispenserBeamBreak()) {
            Dispenser.setSpeed(SetpointConstants.DISPENSER_INTAKE_SPEED)
        } else {
            Dispenser.stop()
        }
    }

    override fun isFinished(): Boolean { return false }

    override fun end(interrupted: Boolean) {}
}
