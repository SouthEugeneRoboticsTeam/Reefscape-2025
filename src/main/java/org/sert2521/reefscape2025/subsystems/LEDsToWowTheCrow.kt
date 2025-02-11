package org.sert2521.reefscape2025.subsystems

import edu.wpi.first.wpilibj.AddressableLED
import edu.wpi.first.wpilibj.AddressableLEDBuffer
import edu.wpi.first.wpilibj.util.Color
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.sert2521.reefscape2025.LEDConstants
import kotlin.math.*

object LEDsToWowTheCrow : SubsystemBase() {

    private val ledStrip = AddressableLED(LEDConstants.LED_ID)
    private val buffer = AddressableLEDBuffer(LEDConstants.LED_LENGTH)

    init{

        ledStrip.setLength(LEDConstants.LED_LENGTH)
        ledStrip.start()
    }

    fun setAll(color: Color) {

        for(i in 0..LEDConstants.LED_LENGTH) {
            buffer.setLED(i, color)
        }
    }

    fun ledCurveCalculate(percent: Double):  Color{

        return Color(
            -(0.0255) * percent.pow(2) + 255,
            (0.0255) * percent.pow(2),
            0.0
        )

        //Curves for Miku colors (If robot is named Miku):
        //Red:    r = (-.0241) * percent.pow(2) + 255
        //Green:  g =  (.0234) * percent.pow(2)
        //Blue:   b =  (.0223) * percent.po2(2)

    }

    fun off() { setAll(Color(0,0,0)) }

    override fun periodic() {
        ledStrip.setData(buffer)
    }

}