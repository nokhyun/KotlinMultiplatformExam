import ImageBitmap.toImageBitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import details.RecipeDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.recipesList
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import recipeslist.RecipesListScreen
import sensor.SensorManager
import sharedelementtransaction.SharedElementsRoot

const val ListScreen = "list"
const val DetailsScreen = "details"

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App(sensorManager: SensorManager, isLarge: Boolean = false) {
    MaterialTheme(typography = Typography.typography) {
        val items by remember { mutableStateOf(recipesList) }
        var width by remember { mutableStateOf(0) }
        var currentScreen by remember { mutableStateOf<Screens>(Screens.RecipesList) }
        var updateIds by remember { mutableStateOf("") }
        val chefImage = remember { mutableStateOf<ImageBitmap?>(null) }
        LaunchedEffect(Unit) {
            withContext(Dispatchers.Default) {
                chefImage.value = resource("chef.png").readBytes().toImageBitmap()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    width = it.size.width
                }
        )

        SharedElementsRoot {
            val sharedTransaction = this

            Box {
                RecipesListScreen(
                    isLarge = isLarge,
                    items = items,
                    width = width,
                    updateIds = updateIds,
                    onClick = { recipe, imageBitmap ->
                        prepareTransition(
                            recipe.id, recipe.description, recipe.title, recipe.image
                        )
                        updateIds = "update_dummy_ids"
                        currentScreen = Screens.RecipeDetails(
                            recipe = recipe,
                            imageBitmap = imageBitmap
                        )
                    }
                )
            }

            when(val screen = currentScreen){
                is Screens.RecipeDetails -> {
                    RecipeDetails(
                        isLarge = isLarge,
                        sensorManager = sensorManager,
                        recipe = screen.recipe,
                        imageBitmap = screen.imageBitmap,
                        chefImage = chefImage.value,
                        goBack = {
                            updateIds = ""
                            sharedTransaction.prepareTransition()
                            prepareTransition(
                                screen.recipe.id,
                                screen.recipe.description,
                                screen.recipe.title,
                                screen.recipe.image
                            )
                            currentScreen = Screens.RecipesList
                        }
                    )
                }
                Screens.RecipesList -> {}
            }
        }
    }
}
