package screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = rememberScreenModel<HomeScreenModel>()

        Text("TEST")
    }
}

interface Screen {
    @Composable
    fun Content()
}

class HomeScreenModel {
    // TODO
}

@Composable
fun <VM> Screen.rememberScreenModel() = remember { HomeScreenModel() }

sealed class BottomNavigationItem(
    val title: String,
    val icon: String,
    val route: String
) {
    data object Home : BottomNavigationItem(title = "HOME", "", "HOME")
}