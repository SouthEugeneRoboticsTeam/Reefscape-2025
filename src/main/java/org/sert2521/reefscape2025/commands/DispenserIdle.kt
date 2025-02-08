package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj.util.Color
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.Dispenser
import org.sert2521.reefscape2025.subsystems.LEDsToWowTheCrow

class DispenserIdle : Command() {

    private val dispenser = Dispenser
    var automaticIntake = true

    init { addRequirements(dispenser) }

    override fun initialize() {}

    override fun execute() {
        if (Dispenser.getRampBeamBreak() || Dispenser.getDispenserBeamBreak()) {
            Dispenser.setSpeed(TunedConstants.DISPENSER_INTAKE_SPEED)
            LEDsToWowTheCrow.setAll(Color(0,255,255))
        } else {
            Dispenser.stop()
        }
    }

    override fun isFinished(): Boolean { return false }

    override fun end(interrupted: Boolean) {
        LEDsToWowTheCrow.setAll(Color(0,0,0))
    }
}
