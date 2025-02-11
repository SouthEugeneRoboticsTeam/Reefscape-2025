package org.sert2521.reefscape2025

import org.sert2521.reefscape2025.commands.drivetrain.VisionAlign
import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.Joystick
import edu.wpi.first.wpilibj2.command.Commands.runOnce
import edu.wpi.first.wpilibj2.command.WaitCommand
import edu.wpi.first.wpilibj2.command.button.CommandXboxController
import edu.wpi.first.wpilibj2.command.button.JoystickButton
import edu.wpi.first.wpilibj2.command.button.Trigger
import org.sert2521.reefscape2025.commands.dispenser.DispenserManualIntake
import org.sert2521.reefscape2025.commands.dispenser.DispenserOuttake
import org.sert2521.reefscape2025.commands.dispenser.DispenserReset
import org.sert2521.reefscape2025.commands.elevator.SetElevator
import org.sert2521.reefscape2025.commands.wrist.SetWrist
import org.sert2521.reefscape2025.commands.ground_intake.GroundIntake
import org.sert2521.reefscape2025.commands.ground_intake.GroundOuttake
import org.sert2521.reefscape2025.subsystems.Dispenser
import org.sert2521.reefscape2025.subsystems.Drivetrain
import org.sert2521.reefscape2025.subsystems.Elevator

// Bindings:
    // Gunner:
        // Stick:
            // Ground Intake -> Trigger [1]
            // Ground/L1 Outtake -> Thumb Bottom [2]
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
        // Ground Outtake -> Left Bumper (LB)
        // Dispenser (Manipulator) Outtake -> Right Bumper (RB)
        // Vision Align -> X

object Input {
    private val driverController = CommandXboxController(0)
    private val gunnerController = Joystick(1)


    // Button Assignment:
        // Drivetrain:
        private val resetDrivetrain = driverController.y()
        private val visionAlign = driverController.x()

        // Wrist:
        private val wristGround = JoystickButton(gunnerController, 12)
        private val wristAlgae = JoystickButton(gunnerController, 13)
        private val wristL1 = JoystickButton(gunnerController, 4)
        private val wristStow = JoystickButton(gunnerController, 3)

        // Ground Intake:
        private val groundIntake = JoystickButton(gunnerController, 1)
        private val groundOuttakeDriver = driverController.leftBumper()
        private val groundOuttakeGunner = JoystickButton(gunnerController, 2)

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

    private val rumble = Trigger { DispenserManualIntake().isFinished }

    init {

        rumble.onTrue(runOnce({setRumble(0.8)}).andThen(WaitCommand(0.2).andThen(runOnce({ setRumble(0.0) }))))

        // Command Assignment
            // Drivetrain
                resetDrivetrain.onTrue(runOnce({ Drivetrain.setNewPose(Pose2d()) }))
                visionAlign.whileTrue(VisionAlign())

            // Wrist
                wristStow.onTrue(SetWrist(SetpointConstants.WRIST_STOW))
                wristL1.onTrue(SetWrist(SetpointConstants.WRIST_L1))
                wristAlgae.onTrue(SetWrist(SetpointConstants.WRIST_ALGAE))
                wristGround.onTrue(SetWrist(SetpointConstants.WRIST_GROUND))

            // Ground Intake
                groundIntake.whileTrue(GroundIntake())
                groundOuttakeDriver.whileTrue(GroundOuttake())
                groundOuttakeGunner.whileTrue(GroundOuttake())

            // Elevator
                elevatorStow.onTrue(SetElevator(SetpointConstants.ELEVATOR_STOW))
                elevatorL2.onTrue(SetElevator(SetpointConstants.ELEVATOR_L2).onlyWhile { !Dispenser.getRampBeamBreak() && !Dispenser.getDispenserBeamBreak() || !Elevator.safeMode })
                elevatorL3.onTrue(SetElevator(SetpointConstants.ELEVATOR_L3).onlyWhile { !Dispenser.getRampBeamBreak() && !Dispenser.getDispenserBeamBreak() || !Elevator.safeMode })
                elevatorL4.onTrue(SetElevator(SetpointConstants.ELEVATOR_L4).onlyWhile { !Dispenser.getRampBeamBreak() && !Dispenser.getDispenserBeamBreak() || !Elevator.safeMode })
                toggleElevatorSafe.onTrue(runOnce({Elevator.toggleSafeMode()}))

            // Dispenser
                dispenserManualIntake.onTrue(DispenserManualIntake())
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