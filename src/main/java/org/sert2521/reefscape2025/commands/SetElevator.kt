package org.sert2521.reefscape2025.commands

import edu.wpi.first.math.controller.ElevatorFeedforward
import edu.wpi.first.math.controller.ProfiledPIDController
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.PIDFFConstants
import org.sert2521.reefscape2025.subsystems.Elevator
/*
hold elevator
    same but not profiled
    ff + pid
    set setpoint at start
 */
class SetElevator(private val setPoint: Double) : Command() {
    private val profilePID = ProfiledPIDController(
        PIDFFConstants.ELEVATOR_P,
        PIDFFConstants.ELEVATOR_I,
        PIDFFConstants.ELEVATOR_D,
        PIDFFConstants.ELEVATOR_TRAPEZOIDAL_CONSTRAINTS
    )
    private val feedForward = ElevatorFeedforward(
        PIDFFConstants.ELEVATOR_S,
        PIDFFConstants.ELEVATOR_G,
        PIDFFConstants.ELEVATOR_V,
    )

    init {
        addRequirements(Elevator)
    }

    override fun initialize() {
        profilePID.reset(Elevator.getDistance())
        profilePID.setGoal(setPoint)
    }

    override fun execute() {
        val pidOutput = profilePID.calculate(Elevator.getDistance())
        val ffOutput = feedForward.calculate(profilePID.setpoint.velocity)
        Elevator.setVoltage(pidOutput + ffOutput)
    }

    override fun isFinished(): Boolean {
        return profilePID.atGoal()
    }

    override fun end(interrupted: Boolean) {
        Elevator.stopMotors()
    }
}
