package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.subsystems.Drivetrain

class VisionAlign : Command() {
    private val drivetrain = Drivetrain


    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(drivetrain)
    }

    override fun initialize() {}

    override fun execute() {}

    override fun isFinished(): Boolean {
        return false
    }

    override fun end(interrupted: Boolean) {}
}
