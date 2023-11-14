import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

actual fun getPlatformName(): String = "Desktop"

@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font = androidx.compose.ui.text.platform.Font("font/$res.ttf", weight, style)
actual class PlatformContext

actual fun getPlatformContext(): PlatformContext {
    TODO("Not yet implemented")
}