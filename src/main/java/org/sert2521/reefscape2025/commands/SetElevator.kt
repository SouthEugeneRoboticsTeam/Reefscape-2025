package org.sert2521.reefscape2025.commands

import edu.wpi.first.math.controller.ElevatorFeedforward
import edu.wpi.first.math.controller.PIDController
import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.Elevator

class SetElevator(private val setPoint: Double) : Command() {
    private val elevatorPID = ProfiledPIDController(
        TunedConstants.ELEVATOR_P,
        TunedConstants.ELEVATOR_I,
        TunedConstants.ELEVATOR_D
    )
    private val elevatorFF = ElevatorFeedforward(
        TunedConstants.ELEVATOR_S,
        TunedConstants.ELEVATOR_G,
        TunedConstants.ELEVATOR_V,
    )

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(Elevator)
    }

    override fun initialize() {
        elevatorPID.setpoint = setPoint
    }

    override fun execute() {
        val PIDcalculate = elevatorPID.calculate(Elevator.getDistance())
        val FFcalculate = elevatorFF.calculate(20)
        Elevator.setVoltage()
    }

    override fun isFinished(): Boolean {
        return elevatorPID.atSetpoint()
    }

    override fun end(interrupted: Boolean) {
        Elevator.stopMotors()
    }
}
