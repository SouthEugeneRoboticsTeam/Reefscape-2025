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

        val red = (((-255.0/(100.0.pow(2))) * percent) + 255).pow(2)
        val green = (255.0/(100.0.pow(2)) * percent).pow(2)
        return Color(red, green, 0.0)

    }

    fun off() { setAll(Color(0,0,0)) }

    override fun periodic() {
        ledStrip.setData(buffer)
    }

}