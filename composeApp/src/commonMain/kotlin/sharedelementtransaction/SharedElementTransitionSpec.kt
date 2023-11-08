package sharedelementtransaction

import androidx.compose.animation.core.AnimationConstants
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing

open class SharedElementTransitionSpec(
    val pathMotionFactory: PathMotionFactory = LinearMotionFactory,
    val waitForFrames: Int = 1,
    val durationMillis: Int = AnimationConstants.DefaultDurationMillis,
    val delayMillis: Int = 0,
    val easing: Easing = FastOutSlowInEasing,
    val direction: TransitionDirection = TransitionDirection.Auto,
    val fadeMode: FadeMode = FadeMode.Cross,
    val faceProgressThresholds: ProgressThresholds? = null,
    val scaleProgressThresholds: ProgressThresholds? = null
)

val DefaultSharedElementsTransitionSpec = SharedElementTransitionSpec()