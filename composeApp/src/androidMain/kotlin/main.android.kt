import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import io.kamel.core.config.KamelConfig
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import sensor.SensorDataManager
import sensor.SensorManager

@Composable
fun MainView(
    kamelConfig: KamelConfig? = null,
    onBackPressed: SharedFlow<Unit>? = null
) {
    val sensorManager = SensorManager()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        val dataManager = SensorDataManager(context)
        dataManager.init()

        val job = scope.launch {
            dataManager.data
                .receiveAsFlow()
                .onEach { sensorManager.listener?.onUpdate(it) }
                .collect()
        }

        onDispose {
            dataManager.cancel()
            job.cancel()
        }
    }

    App(
        sensorManager = sensorManager,
        onBackPressed = onBackPressed
    )
}