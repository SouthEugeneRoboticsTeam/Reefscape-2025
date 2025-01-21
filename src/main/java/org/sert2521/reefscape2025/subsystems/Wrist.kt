package org.sert2521.reefscape2025.subsystems

import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj.DutyCycleEncoder
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sert2521.reefscape2025.CurrentLimits
import org.sert2521.reefscape2025.ElectronicIDs
import org.sert2521.reefscape2025.PhysicalConstants
import kotlin.math.PI

object Wrist: SubsystemBase() {

    private val wristMotor = SparkMax(ElectronicIDs.WRIST_MOTOR_ID, SparkLowLevel.MotorType.kBrushless)
    private val config = SparkMaxConfig()
    private val absEncoder = DutyCycleEncoder(ElectronicIDs.WRIST_ENCODER_ID)

    init {

        config.inverted(false)
        config.smartCurrentLimit(CurrentLimits.WRIST_CURRENT_LIMIT)
        config.idleMode(SparkBaseConfig.IdleMode.kBrake)
        wristMotor.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)

    }

    fun resetEncoder() {}

    fun setVoltage(voltage: Double) { wristMotor.setVoltage(voltage) }

    fun getEncoder() { absEncoder.get() }

    fun getRadians():Double {

        var wristAngle = (absEncoder.get())* PhysicalConstants.WRIST_ENCODER_MULTIPLIER + PhysicalConstants.WRIST_ENCODER_TRANSFORM

        if (wristAngle<-PI){
            wristAngle += 2* PI
        }

        return wristAngle

    }

    fun getAmps(): Double { return wristMotor.outputCurrent }

    fun stop(){ wristMotor.stopMotor() }
}