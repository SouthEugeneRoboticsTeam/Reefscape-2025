package org.sert2521.reefscape2025.subsystems

import com.ctre.phoenix6.configs.CANrangeConfiguration

import com.ctre.phoenix6.hardware.core.CoreCANrange
import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.units.Units
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sert2521.reefscape2025.ElectronicIDs

object Elevator : SubsystemBase() {
    private val motorLeft = SparkMax(ElectronicIDs.ELEVATOR_MOTOR_LEFT, SparkLowLevel.MotorType.kBrushless)
    private val motorRight = SparkMax(ElectronicIDs.ELEVATOR_MOTOR_RIGHT, SparkLowLevel.MotorType.kBrushless)
    private val motorLeftConfig = SparkMaxConfig()
    private val motorRightConfig = SparkMaxConfig()
    private val CANRange = CoreCANrange(ElectronicIDs.TOF_SENSOR, "0")
    private val CANRangeConfigurator = CANRange.configurator
    private val CANRangeConfig = CANrangeConfiguration()

    init {
        CANRangeConfig.FovParams.FOVRangeX = 6.75
        CANRangeConfig.FovParams.FOVRangeY = 6.75
        CANRangeConfig.ProximityParams.ProximityThreshold = 0.0

        CANRangeConfigurator.apply(CANRangeConfig)

        motorRightConfig.smartCurrentLimit(40)
            .idleMode(SparkBaseConfig.IdleMode.kBrake)

        motorLeftConfig.inverted(false)
            .smartCurrentLimit(40)
            .idleMode(SparkBaseConfig.IdleMode.kBrake)

        motorRightConfig.follow(motorLeft, true)

        motorLeft.configure(
            motorLeftConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters
        )
        motorRight.configure(
            motorRightConfig,
            SparkBase.ResetMode.kResetSafeParameters,
            SparkBase.PersistMode.kPersistParameters
        )
    }


    fun setVoltage(voltage: Double) {
        motorLeft.setVoltage(voltage)
    }

    fun getDistance(): Double {
        CANRange.distance.refresh()
        val distance = CANRange.distance.value
        return distance.`in`(Units.Meters)
    }

    fun getTotalAmps(): Double {
        return motorLeft.outputCurrent + motorRight.outputCurrent
    }

    fun stopMotors() {
        motorLeft.stopMotor()
    }
}