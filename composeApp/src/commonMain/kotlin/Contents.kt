import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Contents() {
    var screenType by remember { mutableStateOf(BottomNavigationType.HOME) }

    MaterialTheme {
        when(screenType){
            BottomNavigationType.HOME -> HomeScreen()
            BottomNavigationType.SUB -> SubScreen()
        }

        BottomNavigation(
            onScreenChange = { type ->
                screenType = type
            }
        )
    }
}

@Composable
fun BottomNavigation(
    onScreenChange: (BottomNavigationType) -> Unit
) {
    var label1State by remember { mutableStateOf(true) }
    var label2State by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomNavigation {
            BottomNavigationItem(
                label = { Text("Label1") },
                onClick = {
                    label1State = true
                    label2State = !label2State
                    onScreenChange(BottomNavigationType.HOME)
                },
                icon = {},
                selected = label1State
            )

            BottomNavigationItem(
                label = { Text("Label2") },
                onClick = {
                    label1State = !label1State
                    label2State = true
                    onScreenChange(BottomNavigationType.SUB)
                },
                icon = {},
                selected = label2State
            )
        }
    }
}