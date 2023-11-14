package sensor

import kotlinx.coroutines.channels.Channel
import platform.CoreMotion.CMMotionManager
import platform.Foundation.NSOperationQueue.Companion.currentQueue

class SensorDataManager {
    var motion = CMMotionManager()
    val data = Channel<SensorData>(Channel.UNLIMITED)

    fun startGyros() {
        if (motion.isGyroAvailable()) {
            motion.apply {
                gyroUpdateInterval = 1.0 / 50.0
                startGyroUpdates()
                startDeviceMotionUpdatesToQueue(currentQueue!!) { motion, error ->
                    if (motion != null) {
                        val attitude = motion.attitude
                        data.trySend(
                            SensorData(
                                roll = attitude.roll.toFloat(),
                                pitch = attitude.pitch.toFloat()
                            )
                        )
                    }
                }
            }
        }
    }

    fun stopGyros(){
        motion.stopGyroUpdates()
    }
}