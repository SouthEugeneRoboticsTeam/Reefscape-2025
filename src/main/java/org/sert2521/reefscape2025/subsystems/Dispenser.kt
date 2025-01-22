package org.sert2521.reefscape2025.subsystems

import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.reefscape2025.CurrentLimits
import org.sert2521.reefscape2025.ElectronicIDs


object Dispenser: SubsystemBase() {
    private val dispenserMotor = SparkMax(ElectronicIDs.DISPENSER_MOTOR_ID, SparkLowLevel.MotorType.kBrushless)
    private val beamBreakSensor = DigitalInput(ElectronicIDs.BEAMBREAK_ID)
    private val config = SparkMaxConfig()

    init{

        config.inverted(false)
        config.smartCurrentLimit(CurrentLimits.DISPENSER_CURRENT_LIMIT)
        config.idleMode(SparkBaseConfig.IdleMode.kBrake)
        dispenserMotor.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)

    }

    fun getBeamBreak():Boolean{ return !beamBreakSensor.get() }

    fun setSpeed(speed:Double){ dispenserMotor.set(speed) }

    fun stop(){ dispenserMotor.stopMotor() }

}