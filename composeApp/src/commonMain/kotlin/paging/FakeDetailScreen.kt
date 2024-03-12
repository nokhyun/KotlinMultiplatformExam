package paging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.BaseScreen

class FakeDetailScreen : BaseScreen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val fakeDetailScreenModel = getScreenModel<FakeDetailScreenModel>()
        Scaffold(
            topBar = {
                Toolbar("Title") {
                    navigator?.pop()
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text("FakeDetailScreen")
                    Button({
                        navigator?.pop()
                    }) {
                        Text("Back")
                    }
                }
            }
        }
    }
}