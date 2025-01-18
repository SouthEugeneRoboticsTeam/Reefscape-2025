package org.sert2521.reefscape2025.subsystems

import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import edu.wpi.first.wpilibj.DigitalInput
import org.sert2521.reefscape2025.ElectronicIDs

//change subsystem name to something shorter

object CoralManipulator {
    private val coralManipulatorMotor = SparkMax(ElectronicIDs.CORAL_MANIPULATOR_MOTOR_ID, SparkLowLevel.MotorType.kBrushless)
    private val beamBreakSensor = DigitalInput(ElectronicIDs.BEAMBREAK_ID)
}