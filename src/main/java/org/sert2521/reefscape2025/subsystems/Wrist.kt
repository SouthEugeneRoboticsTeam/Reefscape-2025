package org.sert2521.reefscape2025.subsystems

import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj.DutyCycleEncoder
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sert2521.reefscape2025.ElectronicIDs
import kotlin.math.PI

object Wrist: SubsystemBase() {

    private val wristMotor = SparkMax(ElectronicIDs.WRIST_MOTOR_ID, SparkLowLevel.MotorType.kBrushless)
    private val config = SparkMaxConfig()
    val absEncoder = DutyCycleEncoder(1)

    init {

        config.inverted(false)
        config.smartCurrentLimit(30)
        config.idleMode(SparkBaseConfig.IdleMode.kBrake)
        wristMotor.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)

    }

    fun resetEncoder() {}

    fun setVoltage(voltage: Double) { wristMotor.setVoltage(voltage) }

    fun getEncoder() { absEncoder.get() }

    fun getRadians():Double {
        var wristAngle = (absEncoder.distance+ PI /2).mod(2* PI) - PI /2 + PhysicalConsts.ARM_ENCODER_OFFSET

        return wristAngle
    }

    fun getAmps(): Double { return wristMotor.outputCurrent }

    fun stop(){ wristMotor.stopMotor() }
}