import androidx.compose.ui.graphics.ImageBitmap
import model.Recipe

sealed interface Screens {
    data object RecipesList: Screens
    data class RecipeDetails(
        val recipe: Recipe,
        val imageBitmap: ImageBitmap
    ): Screens
}