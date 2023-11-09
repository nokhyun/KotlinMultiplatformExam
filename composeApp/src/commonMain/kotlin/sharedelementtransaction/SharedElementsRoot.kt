package sharedelementtransaction

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.RecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.toSize

internal val Fullscreen = Modifier.fillMaxSize()
internal val FullscreenLayoutId = Any()

@Composable
fun SharedElementsRoot(
    content: @Composable SharedElementsRootScope.() -> Unit
) {
    // TODO
    val rootState = remember { SharedElementsRootState() }
}

enum class TransitionDirection {
    Auto, Enter, Return
}

enum class FadeMode {
    In, Out, Cross, Through
}

interface SharedElementsRootScope {
    var isRunningTransition: Boolean
    fun prepareTransition(vararg elements: Any)
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
    val overlay: @Composable (SharedElementsTransitionState) -> Unit,
    val bounds: Rect?
)

private sealed class SharedElementTransition(
    val startElement: PositionedSharedElement
) {
    class WaitingForEnElementPosition(startElement: PositionedSharedElement) : SharedElementTransition(startElement)

    class InProgress(
        startElement: PositionedSharedElement,
        val endElement: PositionedSharedElement,
        val onTransitionChanged: (SharedElementTransition?) -> Unit
    ) : SharedElementTransition(startElement)
}

class ChoreographerWrapper {
    private val callbacks = mutableMapOf<SharedElementInfo, () -> Unit>()

    fun postCallback(elementInfo: SharedElementInfo, callback: () -> Unit) {
        if (callbacks.containsKey(elementInfo)) return

        val frameCallback = {
            callbacks.remove(elementInfo)
            callback()
        }

        callbacks[elementInfo] = frameCallback
    }
}

private class SharedElementsTracker(
    private val onTransitionChanged: (SharedElementTransition?) -> Unit
) {
    var state: State = State.Empty
    var pathMotion: PathMotion? = null
    private var _transition: SharedElementTransition? by mutableStateOf(null)
    var transition: SharedElementTransition?
        get() = _transition
        set(value) {
            if (_transition != value) {
                _transition = value

                if (value == null) pathMotion = null
                onTransitionChanged(value)
            }
        }
    val isEmpty: Boolean get() = state is State.Empty

    private fun State.StartElementPositioned.preparedTransition() {
        if (transition !is SharedElementTransition.WaitingForEnElementPosition) {
            transition = SharedElementTransition.WaitingForEnElementPosition(startElement)
        }
    }

    fun prepareTransition() {
        (state as? State.StartElementPositioned)?.preparedTransition()
    }

    fun onElementRegistered(elementInfo: SharedElementInfo): Boolean {
        var shouldHide = false
        val transition = transition
        if (transition is SharedElementTransition.InProgress &&
            elementInfo != transition.startElement.info &&
            elementInfo != transition.endElement.info
        ) {
            state = State.StartElementPositioned(startElement = transition.endElement)
            this.transition = null
        }

        when (val state = state) {
            is State.StartElementPositioned -> {
                if (!state.isRegistered(elementInfo)) {
                    shouldHide = true
                    this.state = State.EndElementRegistered(
                        startElement = state.startElement,
                        endElementInfo = elementInfo
                    )
                    state.preparedTransition()
                }
            }

            is State.StartElementRegistered -> {
                if (elementInfo != state.startElementInfo) {
                    this.state = State.StartElementRegistered(startElementInfo = elementInfo)
                }
            }

            State.Empty -> {
                this.state = State.StartElementRegistered(startElementInfo = elementInfo)
            }

            else -> Unit
        }

        return shouldHide || transition != null
    }

    fun onElementPositioned(element: PositionedSharedElement, setShouldHide: (Boolean) -> Unit) {
        val state = state
        if (state is State.StartElementPositioned && element.info == state.startElementInfo) {
            state.startElement = element
            return
        }

        when (state) {
            is State.EndElementRegistered -> {
                if (element.info == state.endElementInfo) {
                    this.state = State.InTransition
                    val spec = element.info.spec
                    this.pathMotion = spec.pathMotionFactory()
                    transition = SharedElementTransition.InProgress(
                        startElement = state.startElement,
                        endElement = element,
                        onTransitionChanged = {
                            this.state = State.StartElementPositioned(startElement = element)
                            transition = null
                            setShouldHide(false)
                        }
                    )
                }
            }

            is State.StartElementRegistered -> {
                if (element.info == state.startElementInfo) {
                    this.state = State.StartElementPositioned(startElement = element)
                }
            }

            else -> Unit
        }
    }

    fun onElementUnregistered(elementInfo: SharedElementInfo) {
        when (val state = state) {
            is State.EndElementRegistered -> {
                if (elementInfo == state.endElementInfo) {
                    this.state = State.StartElementPositioned(startElement = state.startElement)
                    transition = null
                } else if (elementInfo == state.startElement.info) {
                    this.state = State.StartElementRegistered(startElementInfo = state.endElementInfo)
                    transition = null
                }
            }

            is State.StartElementRegistered -> {
                if (elementInfo == state.startElementInfo) {
                    this.state = State.Empty
                    transition = null
                }
            }

            else -> Unit
        }
    }

    sealed class State {
        data object Empty : State()

        open class StartElementRegistered(
            val startElementInfo: SharedElementInfo
        ) : State() {
            open fun isRegistered(elementInfo: SharedElementInfo): Boolean {
                return elementInfo == startElementInfo
            }
        }

        open class StartElementPositioned(
            var startElement: PositionedSharedElement
        ) : StartElementRegistered(startElement.info)

        class EndElementRegistered(
            startElement: PositionedSharedElement,
            val endElementInfo: SharedElementInfo
        ) : StartElementPositioned(startElement) {

            override fun isRegistered(elementInfo: SharedElementInfo): Boolean {
                return super.isRegistered(elementInfo) || elementInfo == endElementInfo
            }
        }

        data object InTransition : State()
    }
}

internal class SharedElementsTransitionState(
    val fraction: Float,
    val startInfo: SharedElementInfo,
    val startBounds: Rect?,
    val startCompositionLocalContext: Composition,
    val startPlaceholder: @Composable () -> Unit,
    val endInfo: SharedElementInfo?,
    val endBounds: Rect?,
    val endCompositionLocalContext: CompositionLocalContext?,
    val endPlaceholder: (@Composable () -> Unit)?,
    val direction: TransitionDirection?,
    val spec: SharedElementTransitionSpec?,
    val pathMotion: PathMotion?
)

private class SharedElementsRootState {
    private val choreographer = ChoreographerWrapper()
    val scope: SharedElementsRootScope = Scope()
    var trackers by mutableStateOf(mapOf<Any, SharedElementsTracker>())
    var recomposeScope: RecomposeScope? = null
    var rootCoordinates: LayoutCoordinates? = null
    var rootBounds: Rect? = null

    fun onElementRegistered(elementInfo: SharedElementInfo): Boolean {
        return getTracker(elementInfo).onElementRegistered(elementInfo)
    }

    fun onElementPositioned(
        elementInfo: SharedElementInfo,
        compositionLocalContext: CompositionLocalContext,
        placeholder: @Composable () -> Unit,
        overlay: @Composable (SharedElementsTransitionState) -> Unit,
        coordinates: LayoutCoordinates?,
        setShouldHide: (Boolean) -> Unit
    ) {
        val element = PositionedSharedElement(
            info = elementInfo,
            compositionLocalContext = compositionLocalContext,
            placeholder = placeholder,
            overlay = overlay,
            bounds = coordinates?.calculateBoundsInRoot()
        )
    }

    fun onElementDisposed(elementInfo: SharedElementInfo) {
        choreographer.postCallback(elementInfo) {
            val tracker = getTracker(elementInfo)
            tracker.onElementUnregistered(elementInfo)
            if (tracker.isEmpty) trackers = trackers - elementInfo.key
        }
    }

    private fun LayoutCoordinates.calculateBoundsInRoot(): Rect =
        Rect(
            rootCoordinates?.localPositionOf(this, Offset.Zero) ?: positionInRoot(), size.toSize()
        )

    fun onDispose() {
        // ???
    }

    private fun getTracker(elementInfo: SharedElementInfo): SharedElementsTracker {
        return trackers[elementInfo.key] ?: SharedElementsTracker {
            recomposeScope?.invalidate()
            (scope as Scope).isRunningTransition = if (it != null) true else trackers.values.any { it.transition != null }
        }.also { trackers = trackers + (elementInfo.key to it) }
    }

    private inner class Scope : SharedElementsRootScope {
        override var isRunningTransition: Boolean by mutableStateOf(false)

        override fun prepareTransition(vararg elements: Any) {
            elements.forEach {
                trackers[it]?.prepareTransition()
            }
        }
    }
}