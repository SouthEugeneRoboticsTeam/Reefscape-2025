package org.sert2521.reefscape2025.subsystems

import com.revrobotics.spark.SparkBase
import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj.DigitalInput
import edu.wpi.first.wpilibj2.command.SubsystemBase
import org.sert2521.reefscape2025.ElectronicIDs
import org.sert2521.reefscape2025.commands.DispenserIdle


object Dispenser: SubsystemBase() {
    private val dispenserMotor = SparkMax(ElectronicIDs.DISPENSER_MOTOR_ID, SparkLowLevel.MotorType.kBrushless)
    private val dispenserBeambreak = DigitalInput(ElectronicIDs.DISPENSER_BEAMBREAK_ID)
    private val rampBeambreak = DigitalInput(ElectronicIDs.RAMP_BEAMBREAK_ID)
    private val config = SparkMaxConfig()
    var dispenserAutoIntake = true

    init{

        config.inverted(false)
        config.smartCurrentLimit(30)
        config.idleMode(SparkBaseConfig.IdleMode.kBrake)
        dispenserMotor.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)

        dispenserMotor.configure(config, SparkBase.ResetMode.kResetSafeParameters, SparkBase.PersistMode.kPersistParameters)

        defaultCommand = DispenserIdle()

    }

    fun getDispenserBeamBreak():Boolean{ return !dispenserBeambreak.get() }

    fun getRampBeamBreak(): Boolean{ return !rampBeambreak.get() }

    fun setSpeed(speed:Double){ dispenserMotor.set(speed) }

    fun getAmps(): Double { return dispenserMotor.outputCurrent }

    fun stop(){ dispenserMotor.stopMotor() }

    fun changeIntakeMode(){

        if(dispenserAutoIntake){
            defaultCommand = DispenserIdle()
            dispenserAutoIntake = true
        }else{
            defaultCommand = run({stop()})
            dispenserAutoIntake = false
        }
    }

    fun startAutomaticIntake(){
        defaultCommand = DispenserIdle()
    }

}