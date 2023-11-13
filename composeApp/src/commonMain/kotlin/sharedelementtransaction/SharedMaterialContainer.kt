package sharedelementtransaction

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SharedMaterialContainer(
    key: Any,
    screenKey: Any,
    isFullscreen: Boolean = false,
    color: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    transitionSpec: MaterialContainerTransformSpec = defaultSharedElementsTransitionSpec,
    onFractionChanged: ((Float) -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
) {

}

val defaultSharedElementsTransitionSpec = MaterialContainerTransformSpec()

enum class FitMode {
    Auto, Width, Height
}

class MaterialContainerTransformSpec(
    pathMotionFactory: PathMotionFactory = LinearMotionFactory,
    waitForFrames: Int = 1,
    durationMillis: Int = AnimationConstants.DefaultDurationMillis,
    delayMillis: Int = 0,
    easing: Easing = FastOutSlowInEasing,
    direction: TransitionDirection = TransitionDirection.Auto,
    fadeMode: FadeMode = FadeMode.In,
    val fitMode: FitMode = FitMode.Auto,
    val startContainerColor: Color = Color.Transparent,
    val endContainerColor: Color = Color.Transparent,
    fadeProgressThresholds: ProgressThresholds? = null,
    scaleProgressThresholds: ProgressThresholds? = null,
    val scaleMaskProgressThresholds: ProgressThresholds? = null,
    val shapeMaskProgressThresholds: ProgressThresholds? = null
) : SharedElementTransitionSpec(
    pathMotionFactory,
    waitForFrames,
    durationMillis,
    delayMillis,
    easing,
    direction,
    fadeMode,
    fadeProgressThresholds,
    scaleProgressThresholds
)