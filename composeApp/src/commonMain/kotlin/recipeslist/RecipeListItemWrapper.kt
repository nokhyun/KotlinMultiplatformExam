package recipeslist

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

const val perspectiveValue = 0.0004

@Composable
fun RecipeListItemWrapper(
    child: @Composable () -> Unit,
    scrollDirection: Boolean
) {
    val cameraAnimatable = remember { Animatable(7.0f) }
    val scaleAnimatable = remember { Animatable(0.7f) }
    val rotateXAnimatable = remember { Animatable(if (scrollDirection) 60f else -60f) }

    LaunchedEffect(scrollDirection) {
        rotateXAnimatable.animateTo(
            if (scrollDirection) 60f else -60f,
            animationSpec = tween(
                durationMillis = 100,
                easing = CubicBezierEasing(0f, 0.5f, 0.5f, 1f)
            )
        )

        rotateXAnimatable.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = 500,
                easing = CubicBezierEasing(0f, 0.5f, 0.5f, 1f)
            )
        )
    }

    LaunchedEffect(Unit) {
        cameraAnimatable.animateTo(
            8.0f,
            animationSpec = tween(
                durationMillis = 500,
                easing = CubicBezierEasing(0f, 0.5f, 0.5f, 1f)
            )
        )
    }

    LaunchedEffect(Unit) {
        scaleAnimatable.animateTo(
            8.0f,
            animationSpec = tween(
                durationMillis = 700,
                easing = CubicBezierEasing(0f, 0.5f, 0.5f, 1f)
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = scaleAnimatable.value
                scaleY = scaleAnimatable.value
                rotationX = rotateXAnimatable.value
                cameraDistance = cameraAnimatable.value
            }
    ) {
        child()
    }
}