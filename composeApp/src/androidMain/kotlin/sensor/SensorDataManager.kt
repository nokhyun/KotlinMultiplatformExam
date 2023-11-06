package sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.Channel
import kotlin.math.PI

class SensorDataManager(
    context: Context,
) : SensorEventListener {

    private val sensorManager by lazy { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null
    val data: Channel<SensorData> = Channel(Channel.UNLIMITED)

    fun init() {
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        val magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_GRAVITY) {
            gravity = event.values
        }

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values
        }

        if (gravity != null && geomagnetic != null) {
            val r = FloatArray(9)
            val i = FloatArray(9)

            if (SensorManager.getRotationMatrix(r, i, gravity, geomagnetic)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(r, orientation)
                val adjustedPitch = orientation[1] - (PI.toFloat() / 2)

                data.trySend(
                    SensorData(
                        roll = orientation[2],
                        pitch = orientation[1]
                    )
                )
            }
        }
    }

    fun cancel() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
}