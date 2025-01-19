package org.sert2521.reefscape2025.subsystems

import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sert2521.reefscape2025.ElectronicIDs

object Wrist : SubsystemBase() {
    private val wristMotor = SparkMax(ElectronicIDs.WRIST_MOTOR_ID, SparkLowLevel.MotorType.kBrushless)
    private val config = SparkMaxConfig()
    //absolute encoder

    init {
        config.inverted(false)
        config.smartCurrentLimit(30)
        config.idleMode(SparkBaseConfig.IdleMode.kBrake)
        wristMotor.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)
    }

    fun setheight(height:Double) {

    }

    fun stop(){
        wristMotor.stopMotor()
    }
}