package org.sert2521.reefscape2025.subsystems

import com.ctre.phoenix6.StatusSignal
import com.ctre.phoenix6.configs.CANrangeConfiguration
import com.ctre.phoenix6.hardware.CANrange
import com.ctre.phoenix6.hardware.core.CoreCANrange
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import edu.wpi.first.units.measure.Distance
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sert2521.reefscape2025.ElectronicIDs

object ElevatorSubsystem : SubsystemBase() {
    private val motorOne = SparkMax(ElectronicIDs.elevatorMotorOne, SparkLowLevel.MotorType.kBrushless)
    private val motorTwo = SparkMax(ElectronicIDs.elevatorMotorTwo, SparkLowLevel.MotorType.kBrushless)
    private val CANrange = CoreCANrange(ElectronicIDs.TOFSensorID, "0" ) // TODO("set CAN ID)
    private val CANrangeConfiguration = CANrangeConfiguration()

    fun setVoltage(voltage: Double) {
        motorOne.setVoltage(voltage)
        motorTwo.setVoltage(voltage)
    }
    fun getDistance() {

    }
}