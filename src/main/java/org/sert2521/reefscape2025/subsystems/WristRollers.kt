package org.sert2521.reefscape2025.subsystems

import com.revrobotics.spark.SparkLowLevel
import com.revrobotics.spark.SparkMax
import com.revrobotics.spark.config.SparkBaseConfig
import com.revrobotics.spark.config.SparkMaxConfig
import edu.wpi.first.wpilibj2.command.Subsystem
import org.sert2521.reefscape2025.ElectronicIDs

object WristRollers: Subsystem {

    val rollerMotor = SparkMax(ElectronicIDs.GROUND_INTAKE_ROLLER_ID, SparkLowLevel.MotorType.kBrushless)
    private val config = SparkMaxConfig()

    init {

        config.idleMode(SparkBaseConfig.IdleMode.kBrake)
        config.smartCurrentLimit(30)
        config.inverted(false)

    }

    fun setSpeed(speed: Double) { rollerMotor.set(speed) }

    fun stop() { rollerMotor.stopMotor() }

}