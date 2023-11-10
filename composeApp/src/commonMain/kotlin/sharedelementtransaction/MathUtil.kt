package sharedelementtransaction

import androidx.compose.ui.geometry.Rect

internal val Rect.area: Float get() = width * height

internal fun calculateDirection(start: Rect, end: Rect): TransitionDirection =
    if (end.area > start.area) TransitionDirection.Enter else TransitionDirection.Return