package org.sert2521.reefscape2025.commands

import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.subsystems.Wrist

class setWrist: Command() {

    private var angle = Wrist.getEncoder()

}