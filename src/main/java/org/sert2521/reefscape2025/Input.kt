package org.sert2521.reefscape2025

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj2.command.Commands.runOnce
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import edu.wpi.first.wpilibj2.command.button.Trigger
import org.sert2521.reefscape2025.commands.*
import org.sert2521.reefscape2025.subsystems.Dispenser
import org.sert2521.reefscape2025.subsystems.Drivetrain
import org.sert2521.reefscape2025.subsystems.Elevator

// Bindings:
    // Gunner:
        // Stick:
            // Wrist Intake -> Trigger [1]
            // Wrist Outtake -> Thumb Bottom [2]
            // Wrist Stow -> Thumb Left [3]
            // Wrist L1 -> Thumb Right [4]
        // Left Hand:
            // Elevator L2 -> Top Right [7]
            // Elevator L3 -> Top Middle [6]
            // Elevator L4 -> Top Left [5]
            // Elevator Stow -> Bottom Left [10]
            // Dispenser (Manipulator) Reset -> Bottom Right [8]
        // Right Hand:
            // Wrist Algae -> Top Left [13]
            // Wrist Ground -> Top Middle [12]
            // Dispenser (Manipulator) Intake -> Top Right [11]
            // Toggle Automatic Intaking -> Bottom Left [14]
            // Toggle Elevator Safe Mode -> Bottom Middle [15]
    // Driver:
        // Drive -> Left Stick
        // Turn -> Right Stick
        // Reset Drivetrain -> Y
        // Wrist Outtake -> Left Bumper (LB)
        // Dispenser (Manipulator) Outtake -> Right Bumper (RB)


object Input {
    private val driverController = CommandXboxController(0)
    private val gunnerController = Joystick(1)


    // Button Assignment:
        // Drivetrain:
        private val resetDrivetrain = driverController.y()

        // Wrist:
        private val wristGround = JoystickButton(gunnerController, 12)
        private val wristAlgae = JoystickButton(gunnerController, 13)
        private val wristL1 = JoystickButton(gunnerController, 4)
        private val wristStow = JoystickButton(gunnerController, 3)

        // Wrist Rollers:
        private val wristRollerIntake = JoystickButton(gunnerController, 1)
        private val wristRollerOuttakeDriver = driverController.leftBumper()
        private val wristRollerOuttakeGunner = JoystickButton(gunnerController, 2)

        // Elevator:
        private val elevatorStow = JoystickButton(gunnerController, 10)
        private val elevatorL2 = JoystickButton(gunnerController, 7)
        private val elevatorL3 = JoystickButton(gunnerController, 6)
        private val elevatorL4 = JoystickButton(gunnerController, 5)
        private val toggleElevatorSafe = JoystickButton(gunnerController, 15)

        // Dispenser:
        private val toggleAutomaticIntake = JoystickButton(gunnerController, 14)
        private val dispenserManualIntake = JoystickButton(gunnerController, 11)
        private val dispenserOuttake = driverController.rightBumper()
        private val dispenserReset = JoystickButton(gunnerController, 8)

    private val rumble = Trigger { DispenserIntake().isFinished }

    init {

        rumble.onTrue(runOnce({setRumble(0.8)}).andThen(WaitCommand(0.2).andThen(runOnce({ setRumble(0.0) }))))

        // Command Assignment
            // Drivetrain
                resetDrivetrain.onTrue(runOnce({ Drivetrain.setNewPose(Pose2d()) }))

            // Wrist
                wristStow.onTrue(SetWrist(ConfigConstants.WRIST_STOW_SETPOINT))
                wristL1.onTrue(SetWrist(ConfigConstants.WRIST_L1_SETPOINT))
                wristAlgae.onTrue(SetWrist(ConfigConstants.WRIST_ALGAE_SETPOINT))
                wristGround.onTrue(SetWrist(ConfigConstants.WRIST_GROUND_SETPOINT))

            // Wrist Rollers
                wristRollerIntake.whileTrue(WristIntake())
                wristRollerOuttakeDriver.whileTrue(WristOuttake())
                wristRollerOuttakeGunner.whileTrue(WristOuttake())

            // Elevator
                elevatorStow.onTrue(SetElevator(ConfigConstants.ELEVATOR_STOW_SETPOINT))
                elevatorL2.onTrue(SetElevator(ConfigConstants.ELEVATOR_L2_SETPOINT).onlyWhile({!Dispenser.getRampBeamBreak() && !Dispenser.getDispenserBeamBreak() || !Elevator.safeMode}))
                elevatorL3.onTrue(SetElevator(ConfigConstants.ELEVATOR_L3_SETPOINT).onlyWhile({!Dispenser.getRampBeamBreak() && !Dispenser.getDispenserBeamBreak() || !Elevator.safeMode}))
                elevatorL4.onTrue(SetElevator(ConfigConstants.ELEVATOR_L4_SETPOINT).onlyWhile({!Dispenser.getRampBeamBreak() && !Dispenser.getDispenserBeamBreak() || !Elevator.safeMode}))
                toggleElevatorSafe.onTrue(runOnce({Elevator.toggleSafeMode()}))

            // Dispenser
                dispenserManualIntake.onTrue(DispenserIntake())
                dispenserOuttake.whileTrue(DispenserOuttake())
                dispenserReset.onTrue(DispenserReset())
                toggleAutomaticIntake.onTrue(runOnce({Dispenser.changeIntakeMode()}))

    }

    var rotationOffset = Rotation2d(0.0)

    fun getJoystickX():Double{ return -driverController.leftX }

    fun getJoystickY():Double{ return -driverController.leftY }

    fun getJoystickZ():Double { return -driverController.rightX }

    fun getRotOffset(): Rotation2d { return rotationOffset }

    fun setRumble(amount: Double) { driverController.setRumble(GenericHID.RumbleType.kBothRumble, amount) }
}