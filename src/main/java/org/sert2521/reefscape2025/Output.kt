package org.sert2521.reefscape2025

import edu.wpi.first.math.geometry.Pose2d
import edu.wpi.first.math.geometry.Rotation2d
import edu.wpi.first.wpilibj.DataLogManager
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.smartdashboard.Field2d
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.reefscape2025.subsystems.*
import java.io.File

object Output : SubsystemBase() {
    private val values = mutableListOf<Pair<String, () -> Double>>()
    private val bools = mutableListOf<Pair<String, () -> Boolean>>()
    private val field = Field2d()
    private val visionField = Field2d()
    private val visionTargetPose = Field2d()
    private val visionEstimation = Field2d()
    private val testField = Field2d()

    private var drivetrainAmps: Array<Pair<Double, Double>> = arrayOf()
    private var dispenserAmps: Double = 0.0
    private var wristAmps: Double = 0.0
    private var wristRollerAmps: Double = 0.0
    private var elevatorAmps: Double = 0.0
    private var totalAmps: Double = 0.0
    private var elevatorHeight: Double = 0.0

    init {

        val storageDevices = File("/media").listFiles()

        if (storageDevices != null) {
            if (storageDevices.isNotEmpty()) {
                DataLogManager.start(storageDevices[0].absolutePath)
                DriverStation.startDataLog(DataLogManager.getLog())
            }
        }

        values.add(Pair("Drive 1 Speed Drive") { Drivetrain.getStates()[0].speedMetersPerSecond })
        values.add(Pair("Drive 2 Speed Drive") { Drivetrain.getStates()[1].speedMetersPerSecond })
        values.add(Pair("Drive 3 Speed Drive") { Drivetrain.getStates()[2].speedMetersPerSecond })
        values.add(Pair("Drive 4 Speed Drive") { Drivetrain.getStates()[3].speedMetersPerSecond })

        values.add(Pair("Drive 1 Goal Drive") {Drivetrain.getGoals()[0].speedMetersPerSecond})

        values.add(Pair("Drive 1 Amps Drive") { drivetrainAmps[0].first })
        values.add(Pair("Drive 2 Amps Drive") { drivetrainAmps[1].first })
        values.add(Pair("Drive 3 Amps Drive") { drivetrainAmps[2].first })
        values.add(Pair("Drive 4 Amps Drive") { drivetrainAmps[3].first })

        values.add(Pair("Drive 1 Amps Angle") { drivetrainAmps[0].second })
        values.add(Pair("Drive 2 Amps Angle") { drivetrainAmps[1].second })
        values.add(Pair("Drive 3 Amps Angle") { drivetrainAmps[2].second })
        values.add(Pair("Drive 4 Amps Angle") { drivetrainAmps[3].second })

        values.add(Pair("Drive 1 Health") {Drivetrain.getHealth(0)})
        values.add(Pair("Drive 2 Health") {Drivetrain.getHealth(1)})
        values.add(Pair("Drive 3 Health") {Drivetrain.getHealth(2)})
        values.add(Pair("Drive 4 Health") {Drivetrain.getHealth(3)})

        values.add(Pair("Wrist Angle") { Wrist.getRadians() })
        values.add(Pair("Elevator Height") { Elevator.getDistance() })

        values.add(Pair("Dispenser Amps") { Dispenser.getAmps() })
        values.add(Pair("Wrist Amps") { Wrist.getAmps() })
        values.add(Pair("Wrist Roller Amps") { WristRollers.getAmps() })
        values.add(Pair("Elevator Amps") { Elevator.getTotalAmps() })

        bools.add(Pair("Beambreak") { Dispenser.getBeamBreak() })

        SmartDashboard.putData("Vision Field", visionField)
        SmartDashboard.putData("Vision Pose Target", visionTargetPose)
        SmartDashboard.putData("Field", field)
        SmartDashboard.putData("Vision Estimation", visionEstimation)
        SmartDashboard.putData("Test Field", testField)

        update()

    }

    fun update(){
        drivetrainAmps = Drivetrain.getAmps()
        dispenserAmps = Dispenser.getAmps()
        wristAmps = Wrist.getAmps()
        wristRollerAmps = WristRollers.getAmps()
        totalAmps = drivetrainAmps[0].first + drivetrainAmps[1].first + drivetrainAmps[2].first + drivetrainAmps[3].first + drivetrainAmps[0].second + drivetrainAmps[1].second + drivetrainAmps[2].second + drivetrainAmps[3].second + dispenserAmps + wristAmps + wristRollerAmps + elevatorAmps
        elevatorAmps = Elevator.getTotalAmps()
        elevatorHeight = Elevator.getDistance()

        for (value in values) {
            SmartDashboard.putNumber("Output/${value.first}", value.second())
        }

        for (bool in bools) {
            SmartDashboard.putBoolean("Output/${bool.first}", bool.second())
        }

        testField.robotPose = Pose2d(Drivetrain.getPose().translation, Rotation2d(0.0))

        field.robotPose = Drivetrain.getPose()


        visionField.robotPose = Drivetrain.getVisionPose()

        field.robotPose = Drivetrain.getPose()

        visionTargetPose.robotPose = Pose2d(Drivetrain.getVisionPose().translation, Drivetrain.getVisionPose().rotation)

    }
}