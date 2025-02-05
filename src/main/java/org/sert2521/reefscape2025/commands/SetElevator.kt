package org.sert2521.reefscape2025.commands

import edu.wpi.first.math.controller.ElevatorFeedforward
import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.TunedConstants
import org.sert2521.reefscape2025.subsystems.Elevator

class SetElevator(private val setPoint: Double) : Command() {
    private val profilePID = ProfiledPIDController(
        TunedConstants.ELEVATOR_P,
        TunedConstants.ELEVATOR_I,
        TunedConstants.ELEVATOR_D,
        TunedConstants.ELEVATOR_TRAPEZOIDAL_CONSTRAINTS
    )
    private val feedForward = ElevatorFeedforward(
        TunedConstants.ELEVATOR_S,
        TunedConstants.ELEVATOR_G,
        TunedConstants.ELEVATOR_V,
    )

    init {
        // each subsystem used by the command must be passed into the addRequirements() method
        addRequirements(Elevator)
    }

    override fun initialize() {
        profilePID.setGoal(setPoint)
    }

    override fun execute() {
        val pidVal = profilePID.calculate(Elevator.getDistance())
        Elevator.setVoltage(pidVal + feedForward.calculate(profilePID.setpoint.velocity))
    }

    override fun isFinished(): Boolean {
        return profilePID.atSetpoint()
    }

    override fun end(interrupted: Boolean) {
        Elevator.stopMotors()
    }
}
