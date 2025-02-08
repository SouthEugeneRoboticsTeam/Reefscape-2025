package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj.util.Color
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.subsystems.LEDsToWowTheCrow

class WowTheCrow(private var r: Int, private var g: Int, private var b: Int) : Command() {

    init {
        addRequirements(LEDsToWowTheCrow)
    }

    override fun initialize() {}

    override fun execute() {
        LEDsToWowTheCrow.setAll(Color(r, g, b))
    }

    override fun isFinished(): Boolean {
        return false
    }

    override fun end(interrupted: Boolean) {
        LEDsToWowTheCrow.off()
    }
}
