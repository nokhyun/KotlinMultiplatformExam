import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.window.ComposeUIViewController
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import sensor.SensorDataManager
import sensor.SensorManager

fun MainViewController() = ComposeUIViewController {
    val sensorManager = SensorManager()
    val scope = rememberCoroutineScope()

    val kamelConfig = KamelConfig {
        takeFrom(KamelConfig.Default)
    }

    DisposableEffect(Unit) {
        val dataManager = SensorDataManager()
        dataManager.startGyros()

        val job = scope.launch {
            dataManager.data
                .receiveAsFlow()
                .onEach { sensorManager.listener?.onUpdate(it) }
                .collect()
        }

        onDispose {
            job.cancel()
            dataManager.stopGyros()
        }
    }

    App(kamelConfig, sensorManager)
}
