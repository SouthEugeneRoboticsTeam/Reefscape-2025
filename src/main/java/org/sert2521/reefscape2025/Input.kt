package org.sert2521.reefscape2025

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj2.command.Commands.runOnce
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import edu.wpi.first.wpilibj2.command.button.Trigger
import org.sert2521.reefscape2025.commands.*
import org.sert2521.reefscape2025.subsystems.Dispenser
import org.sert2521.reefscape2025.subsystems.Drivetrain

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
    // Driver:
        // Drive -> Left Stick
        // Turn -> Right Stick L-R
        // Reset Drivetrain -> 2-square "view" button [8]
        // Wrist Outtake -> B [2]
        // Dispenser (Manipulator) Outtake -> Right Bumper (RB) [6]


object Input {
    private val driverController = XboxController(0)
    private val gunnerController = Joystick(1)


    // Button Assignment:
        // Drivetrain:
        private val resetDrivetrain = JoystickButton(driverController, 8)

        // Wrist:
        private val wristGround = JoystickButton(gunnerController, 12)
        private val wristAlgae = JoystickButton(gunnerController, 13)
        private val wristL1 = JoystickButton(gunnerController, 4)
        private val wristStow = JoystickButton(gunnerController, 3)

        // Wrist Rollers:
        private val wristRollerIntake = JoystickButton(gunnerController, 1)
        private val wristRollerOuttakeDriver = JoystickButton(driverController, 2)
        private val wristRollerOuttakeGunner = JoystickButton(gunnerController, 2)

        // Elevator:
        private val elevatorStow = JoystickButton(gunnerController, 10)
        private val elevatorL2 = JoystickButton(gunnerController, 7)
        private val elevatorL3 = JoystickButton(gunnerController, 6)
        private val elevatorL4 = JoystickButton(gunnerController, 5)

        // Dispenser:
        private val dispenserIntake = Trigger{ Dispenser.getRampBeamBreak() } // Intake when ramp beambreak is triggered
        private val dispenserOuttake = JoystickButton(driverController, 6)
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
                elevatorL2.onTrue(SetElevator(ConfigConstants.ELEVATOR_L2_SETPOINT))
                elevatorL3.onTrue(SetElevator(ConfigConstants.ELEVATOR_L3_SETPOINT))
                elevatorL4.onTrue(SetElevator(ConfigConstants.ELEVATOR_L4_SETPOINT))

            // Dispenser
                dispenserIntake.onTrue(DispenserIntake())
                dispenserOuttake.whileTrue(DispenserOuttake())
                dispenserReset.onTrue(DispenserReset()) // May need to be dispenserReset.whileTrue(DispenserIntake())

    }

    var rotationOffset = Rotation2d(0.0)

    fun getJoystickX():Double{ return -driverController.leftX }

    fun getJoystickY():Double{ return -driverController.leftY }

    fun getJoystickZ():Double { return -driverController.rightX }

    fun getRotOffset(): Rotation2d { return rotationOffset }

    private fun setRumble(amount: Double) { driverController.setRumble(GenericHID.RumbleType.kBothRumble, amount) }
}