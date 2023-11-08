package sharedelementtransaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Rect

@Composable
fun SharedElementsRoot(
    content: @Composable SharedElementsRootScope.() -> Unit
) {
    val rootState = remember { SharedElementsRootState() }
}

enum class TransitionDirection {
    Auto, Enter, Return
}

enum class FadeMode {
    In, Out, Cross, Through
}

interface SharedElementsRootScope {
    val isRunningTransition: Boolean
    fun preparedTransition(vararg elements: Any)
}

open class SharedElementInfo(
    val key: Any,
    val screenKey: Any,
    val spec: SharedElementTransitionSpec,
    val onFractionChanged: ((Float) -> Unit)?
)

private class PositionedSharedElement(
    val info: SharedElementInfo,
    val compositionLocalContext: CompositionLocalContext,
    val placeholder: @Composable () -> Unit,
    val overlay: @Composable (SharedElementsRootState) -> Unit,
    val bounds: Rect
)

private sealed class SharedElementTransition(
    val startElement: PositionedSharedElement
) {

}

private class SharedElementsRootState {
    // TODO
}