package org.sert2521.reefscape2025.commands

import edu.wpi.first.math.controller.ElevatorFeedforward
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.PIDFFConstants
import org.sert2521.reefscape2025.subsystems.Elevator

class HoldElevator : Command() {

    private val pid = PIDController(
        PIDFFConstants.ELEVATOR_P,
        PIDFFConstants.ELEVATOR_I,
        PIDFFConstants.ELEVATOR_D,
    )
    private val feedForward = ElevatorFeedforward(
        PIDFFConstants.ELEVATOR_S,
        PIDFFConstants.ELEVATOR_G,
        PIDFFConstants.ELEVATOR_V,
    )

    private var setPoint = 0.0

    init { addRequirements(Elevator) }

    override fun initialize() { setPoint = Elevator.getDistance() }

    override fun execute() {

        val pidOutput = pid.calculate(Elevator.getDistance())
        val ffOutput = feedForward.calculate(0.0)
        Elevator.setVoltage(pidOutput + ffOutput)

    }

    override fun isFinished(): Boolean { return false }

    override fun end(interrupted: Boolean) {}
}
