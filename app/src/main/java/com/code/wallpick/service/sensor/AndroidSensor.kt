package com.code.wallpick.service.sensor


import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

abstract class AndroidSensor(
    protected val context: Context,
    private val sensorFeature: String,
    private val sensorType: Int
) : SensorEventListener {
    protected var onSensorValuesChanged: ((List<Float>) -> Unit)? = null

    var isSensorListening: Boolean = false

    fun setOnSensorValuesChangedListener(listener: (List<Float>) -> Unit) {
        onSensorValuesChanged = listener
    }

    val doesSensorExist: Boolean
        get() = context.packageManager.hasSystemFeature(sensorFeature)

    lateinit var sensorManager: SensorManager
    var sensor: Sensor? = null

    fun startListening() {
        if (!doesSensorExist) return
        if (!::sensorManager.isInitialized && sensor == null) {
            sensorManager = context.getSystemService(SensorManager::class.java) as SensorManager
            sensor = sensorManager.getDefaultSensor(sensorType)
        }
        sensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
            isSensorListening = true
        }
    }

    fun stopListening() {
        if (!doesSensorExist || !::sensorManager.isInitialized) return
        sensorManager.unregisterListener(this)
        isSensorListening = false
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (!doesSensorExist) return
        if (event?.sensor?.type == sensorType) {
            onSensorValuesChanged?.invoke(event.values.toList())
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


}