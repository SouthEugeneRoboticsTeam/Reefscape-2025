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

object Elevator : SubsystemBase() {
    private val motorOne = SparkMax(ElectronicIDs.elevatorMotorOne, SparkLowLevel.MotorType.kBrushless)
    private val motorTwo = SparkMax(ElectronicIDs.elevatorMotorTwo, SparkLowLevel.MotorType.kBrushless)
    private val motorOneConfig = SparkMaxConfig()
    private val motorTwoConfig = SparkMaxConfig()
    private val CANRange = CoreCANrange(ElectronicIDs.TOFSensorID, "0")
    private val CANRangeConfigurator = CANRange.configurator
    private val CANRangeConfig = CANrangeConfiguration()

    init {
        CANRangeConfig.FovParams.FOVRangeX = 6.75
        CANRangeConfig.FovParams.FOVRangeY = 6.75
        CANRangeConfig.ProximityParams.ProximityThreshold = 0.05
        CANRangeConfig.ProximityParams.ProximityHysteresis = 0.005

        CANRangeConfigurator.apply(CANRangeConfig)

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

    fun getDistance(): Double {
        CANRange.distance.refresh()
        val distance = CANRange.distance.value
        return distance.`in`(distance.unit())
    }

    fun stopMotors() {
        motorTwo.stopMotor()
        motorOne.stopMotor()
    }
}