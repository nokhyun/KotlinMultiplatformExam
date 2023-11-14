import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

expect fun getPlatformName(): String

@Composable
expect fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font

expect class PlatformContext
expect fun getPlatformContext(): PlatformContext

fun logger(log: () -> Any?) {
    println("EmmExam Log: ${log()}")
}
