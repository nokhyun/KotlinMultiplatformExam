package paging

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import logger
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.mp.KoinPlatform

class FakePagingScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<FakeApiScreenModel>()
        val result = screenModel.fakePagingData.collectAsLazyPagingItems()
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(result.itemSnapshotList.items) { item ->
                asyncPainterResource(item.profilePicture).let {
                    when (val image = it) {
                        is Resource.Failure -> {
                            logger { "${image.exception}" }
                            Text("paging Failure")
                        }

                        is Resource.Loading -> {
                            Text("paging Loading")
                        }

                        is Resource.Success -> {
                            Column {
                                Image(
                                    modifier = Modifier.fillMaxWidth()
                                        .height(200.dp),
                                    painter = image.value,
                                    contentScale = ContentScale.FillBounds,
                                    contentDescription = null
                                )
                                Text(
                                    text = "test: ${item.firstName + item.lastName}",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily.SansSerif,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    /** Base로 빠져버려! */
    @Composable
    inline fun <reified T : ScreenModel> getScreenModel(
        qualifier: Qualifier? = null,
        noinline parameters: ParametersDefinition? = null,
    ): T {
        val koin = KoinPlatform.getKoin()
        return rememberScreenModel(tag = qualifier?.value) { koin.get(qualifier, parameters) }
    }
}