package recipeslist

import ImageBitmap.toImageBitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import model.Recipe
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RecipeListItem(
    recipe: Recipe,
    updateIds: String,
    width: Int,
    onClick: (recipe: Recipe, bitmap: ImageBitmap) -> Unit
) {
    val image = remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        image.value = resource(recipe.image).readBytes().toImageBitmap()
    }

    Box {
        Box(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(35.dp),
                    clip = true,
                    ambientColor = Color(0xffCE5A01),
                    spotColor = Color(0xffCE5A01)
                )
                .width(width.dp)
                .aspectRatio(1.5f)
                .background(recipe.bgColor, RoundedCornerShape(35.dp))
                .fillMaxHeight()
                .clickable {
                    onClick(recipe, image.value!!)
                }
        ){
            // TODO
        }
    }
}