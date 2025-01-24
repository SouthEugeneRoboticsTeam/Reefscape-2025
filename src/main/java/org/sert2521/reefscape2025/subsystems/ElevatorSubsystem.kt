package org.sert2521.reefscape2025.subsystems

import com.ctre.phoenix6.configs.CANrangeConfiguration
import com.ctre.phoenix6.hardware.core.CoreCANrange
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sert2521.reefscape2025.ElectronicIDs

object ElevatorSubsystem : SubsystemBase() {
    private val motorOne = SparkMax(ElectronicIDs.elevatorMotorOne, SparkLowLevel.MotorType.kBrushless)
    private val motorTwo = SparkMax(ElectronicIDs.elevatorMotorTwo, SparkLowLevel.MotorType.kBrushless)
    private val motorOneConfig = SparkMaxConfig()
    private val motorTwoConfig = SparkMaxConfig()
    private val CANrange = CoreCANrange(ElectronicIDs.TOFSensorID, "0") // TODO("set CAN ID)
    private val CANrangeConfiguration: CANrangeConfiguration = CANrangeConfiguration()

    init {

        motorTwoConfig.inverted(false)
            .smartCurrentLimit(40)
            .idleMode(SparkBaseConfig.IdleMode.kBrake)

        motorOneConfig.inverted(false)
            .smartCurrentLimit(40)
            .idleMode(SparkBaseConfig.IdleMode.kBrake)

        motorOne.configure(
            motorOneConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters
        )
        motorTwo.configure(
            motorTwoConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters
        )
    }


    fun setVoltage(voltage: Double) {
        motorOne.setVoltage(voltage)
        motorTwo.setVoltage(voltage)
    }

    fun setSpeed(speed: Double) {
        motorOne.set(speed)
        motorTwo.set(speed)
    }

    fun getDistance(): Double {
        CANrange.distance.refresh()
        val distanceObject = CANrange.distance.value
        val units = distanceObject.unit() // not sure what the units are by default - probably meters

        return distanceObject.`in`(units)
    }
}