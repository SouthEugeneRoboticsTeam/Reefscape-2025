package org.sert2521.reefscape2025

import com.pathplanner.lib.auto.AutoBuilder
import com.pathplanner.lib.config.ModuleConfig
import com.pathplanner.lib.config.RobotConfig
import com.pathplanner.lib.controllers.PPHolonomicDriveController
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj2.command.Command
import edu.wpi.first.wpilibj2.command.Commands
import org.sert2521.reefscape2025.commands.dispenser.DispenserManualIntake
import org.sert2521.reefscape2025.commands.dispenser.DispenserOuttake
import org.sert2521.reefscape2025.commands.elevator.SetElevator
import org.sert2521.reefscape2025.commands.wrist.SetWrist
import org.sert2521.reefscape2025.commands.wristrollers.WristIntake
import org.sert2521.reefscape2025.commands.wristrollers.WristOuttake
import org.sert2521.reefscape2025.subsystems.Drivetrain

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the [Robot]
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 *
 * In Kotlin, it is recommended that all your Subsystems are Kotlin objects. As such, there
 * can only ever be a single instance. This eliminates the need to create reference variables
 * to the various subsystems in this container to pass into to commands. The commands can just
 * directly reference the (single instance of the) object.
 */
object Autos {

    private var autoChooser = SendableChooser<Command>()
    private val defaultAutoCommand = Commands.none()

    private var commandList = mapOf<String, Command>(

        "Wrist to L1" to SetWrist(TunedConstants.WRIST_L1_SETPOINT).asProxy(),
        "Wrist to Ground" to SetWrist(TunedConstants.WRIST_GROUND_SETPOINT).asProxy(),
        "Stow Wrist" to SetWrist(TunedConstants.WRIST_STOW_SETPOINT).asProxy(),

        "L1 Outtake" to WristOuttake().asProxy(),

        "Dispenser Intake" to DispenserManualIntake().asProxy(),
        "Dispenser Outtake" to DispenserOuttake().asProxy(),

        "Elevator Stow" to SetElevator(TunedConstants.ELEVATOR_STOW_SETPOINT).asProxy(),
        "Elevator L2" to SetElevator(TunedConstants.ELEVATOR_L2_SETPOINT).asProxy(),
        "Elevator L3" to SetElevator(TunedConstants.ELEVATOR_L3_SETPOINT).asProxy(),
        "Elevator L4" to SetElevator(TunedConstants.ELEVATOR_L4_SETPOINT).asProxy()

    )

    init{
        AutoBuilder.configure(
            Drivetrain::getPose,
            Drivetrain::setNewPose,
            Drivetrain::getRelativeSpeeds,
            Drivetrain::drive,
            PPHolonomicDriveController(
                DrivetrainConstants.autoTranslationPID,
                DrivetrainConstants.autoRotationPID
            ),
            RobotConfig(
                PhysicalConstants.robotMass,
                PhysicalConstants.momentOfInertia,
                ModuleConfig(
                    DrivetrainConstants.WHEEL_RADIUS_METERS,
                    DrivetrainConstants.MAX_ANGULAR_SPEED,
                    DrivetrainConstants.WHEEL_COF,
                    DrivetrainConstants.driveMotorGearbox,
                    DrivetrainConstants.DRIVE_AUTO_CURRENT_LIMIT.toDouble(),
                    1
                ),
                *DrivetrainConstants.moduleTranslations
            ),
            {false}
        )

    }

    fun getAutonomousCommand(): Command? {
        // TODO: Implement properly
        return autoChooser.selected
    }
}