package tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import common.Preference
import getPlatformContext
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource

data object FavoriteTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = "Favorite"
            val icon = rememberVectorPainter(Icons.Default.Favorite)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        FakePagingLazyVerticalGridScreen()
    }
}

@Composable
fun FakePagingLazyVerticalGridScreen(
    preference: Preference = Preference(getPlatformContext())
) {
    val allKey = preference.allKey()
    val isEmptyScreen by remember { mutableStateOf(allKey.isEmpty()) }

    if (isEmptyScreen) {
        EmptyFavoriteScreen()
    } else {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(allKey.size) {
                LazyGridFavoriteItem(allKey[it])
            }
        }
    }
}

@Composable
fun EmptyFavoriteScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Empty Favorite!!!")
    }
}


@Composable
fun LazyGridFavoriteItem(
    img: String
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            asyncPainterResource(img).apply {
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
                                .height(200.dp),
                            painter = this.value,
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}