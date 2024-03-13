package paging

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.BaseScreen


class FakeDetailScreen(
    private val user: User,
    val painter: Painter
) : BaseScreen() {

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
                    Column(
                        modifier = Modifier
                            .width(240.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .size(240.dp),
                            painter = painter,
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {

                            }) {
                            Text(
                                text = user.firstName,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}