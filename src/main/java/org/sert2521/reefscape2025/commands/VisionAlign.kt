import edu.wpi.first.hal.simulation.EncoderDataJNI
import edu.wpi.first.math.MathUtil
import edu.wpi.first.math.kinematics.ChassisSpeeds
import edu.wpi.first.wpilibj2.command.Command
import org.sert2521.reefscape2025.DrivetrainConstants
import org.sert2521.reefscape2025.Input
import org.sert2521.reefscape2025.libraries.LimelightHelpers
import org.sert2521.reefscape2025.subsystems.Drivetrain

class VisionAlign(): Command() {

    init{ addRequirements(Drivetrain) }

    fun limelightAimProportional(): Double {

        val kP = DrivetrainConstants.VISION_AIM_P

        var targetingAngularVelocity = LimelightHelpers.getTX("limelight") * kP

        targetingAngularVelocity *= DrivetrainConstants.MAX_ANGULAR_SPEED

        targetingAngularVelocity *= -1.0

        return targetingAngularVelocity

    }

    fun limelightRangeProportional(): Double {

        val kP = DrivetrainConstants.VISION_RANGE_P
        var targetingForwardSpeed = LimelightHelpers.getTY("limelight") * kP
        targetingForwardSpeed *= DrivetrainConstants.MAX_ANGULAR_SPEED
        targetingForwardSpeed *= -1.0
        return targetingForwardSpeed

    }

    private fun drive(fieldRelative: Boolean) {

        var fieldRelative = fieldRelative

        var xSpeed: Double = (DrivetrainConstants.VISION_X_SPEED_LIMIT.calculate(MathUtil.applyDeadband(Input.getLeftJoystickY(), 0.02)) * DrivetrainConstants.MAX_ANGULAR_SPEED)
        val ySpeed: Double = (DrivetrainConstants.VISION_Y_SPEED_LIMIT.calculate(MathUtil.applyDeadband(Input.getLeftJoystickX(), 0.02)) * DrivetrainConstants.MAX_ANGULAR_SPEED)
        var rot: Double = (DrivetrainConstants.VISION_ROT_SPEED_LIMIT.calculate(MathUtil.applyDeadband(Input.getRightJoystickX(), 0.02)) * DrivetrainConstants.MAX_ROT_SPEED)

        // while the A-button is pressed, overwrite some of the driving values with the output of our limelight methods
        if (Input.getAButton()) {
            val limelightRot = limelightAimProportional()
            rot = limelightRot

            val limelightForward = limelightRangeProportional()
            xSpeed = limelightForward

            //while using Limelight, turn off field-relative driving.
            fieldRelative = false
        }

        Drivetrain.drive(ChassisSpeeds( xSpeed, ySpeed, rot))
    }

}
