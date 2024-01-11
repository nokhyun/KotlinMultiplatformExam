package paging

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.cash.paging.LoadStateError
import app.cash.paging.LoadStateLoading
import app.cash.paging.compose.collectAsLazyPagingItems
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import common.Preference
import getPlatformContext
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
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
            items(result.itemCount) {
                val item = result[it]!!
                LazyGridItem(item)
            }

            result.loadState.apply {
                when {
                    refresh is LoadStateLoading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    refresh is LoadStateError -> {
                        item {
                            Button({
                                result.retry()
                            }) {
                                Text("Retry!!!")
                            }
                        }
                    }

                    append is LoadStateError -> {
                        item {
                            Button({
                                result.retry()
                            }) {
                                Text("Retry!!!")
                            }
                        }
                    }

                    else -> {
                        // TODO
                        logger { "loadState: $this" }
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

@Composable
fun LazyGridItem(
    item: User,
    preference: Preference = Preference(getPlatformContext())
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            asyncPainterResource(item.profilePicture).apply {
                when (this) {
                    is Resource.Loading -> {
                        CircularProgressIndicator()
                    }

                    is Resource.Failure -> {
                    }

                    is Resource.Success -> {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clickable {
                                    logger { preference.get(key = item.id.toString()) }
                                },
                            painter = this.value,
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null
                        )
                    }
                }
            }

            var isClicked by remember { mutableStateOf(false) }

            Image(
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
                    .clickable {
                        isClicked = !isClicked
                        preference.set(key = item.profilePicture, value = item.id.toString())
                    },
                colorFilter = ColorFilter.tint(
                    if (isClicked || preference.allValue()
                            .any { it == item.id.toString() }
                    ) Color.Red else Color.Gray
                ),
                imageVector = Icons.Default.Favorite,
                contentDescription = null
            )
        }

        Text(
            text = "name: ${item.firstName + item.lastName}",
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        )
    }
}