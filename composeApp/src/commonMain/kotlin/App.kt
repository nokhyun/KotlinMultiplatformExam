import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import io.github.aakira.napier.Napier
import io.kamel.core.config.KamelConfig
import io.kamel.image.config.LocalKamelConfig
import paging.FakePagingScreen
import tab.TabNavigatorExam

@Composable
fun App(kamelConfig: KamelConfig? = null) {
    kamelConfig?.also {
        CompositionLocalProvider(LocalKamelConfig provides it) {
            when (getPlatformName()) {
                "iOS",
                "Android",
                -> TabNavigatorExam()

                else -> NavigatorExam()
            }
        }
    }
}

@Composable
fun NavigatorExam() {
    Navigator(
        screen = HomeScreen(),
        onBackPressed = { currentScreen ->
            logger { "Navigator Pop Screen!! :: $currentScreen" }
            true
        }
    )
}

@Composable
fun FakePagingExam() {
    Napier.e { "[FakePagingExam]" }
    Navigator(
        screen = FakePagingScreen(),
        onBackPressed = { currentScreen ->
            logger { "Navigator Pop Screen!! :: $currentScreen" }
            true
        }
    )
}

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        EmptyHomeScreen()
    }
}

@Composable
fun EmptyHomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "Hello HomeScreen",
            fontSize = 24.sp,
            color = Color.White
        )
    }
}