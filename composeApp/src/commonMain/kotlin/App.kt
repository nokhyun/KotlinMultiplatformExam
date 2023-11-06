import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import screens.HomeScreen

@Composable
fun App(
    homeScreen: HomeScreen = HomeScreen()
) {

    Navigator(homeScreen)
//    Contents()
}

@Composable
fun Navigator(
    homeScreen: HomeScreen
){
    MaterialTheme {
        homeScreen.Content()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen() {
    var greetingText by remember { mutableStateOf(GREETING) }
    var showImage by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            greetingText = "Compose on ${getPlatformName()}: $GREETING"
            showImage = !showImage
        }) {
            Text(greetingText)
        }
        AnimatedVisibility(showImage) {
            Image(
                painterResource("compose-multiplatform.xml"),
                null
            )
        }
    }
}

@Composable
fun SubScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Welcome SubScreen!")
    }
}

