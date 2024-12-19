package com.example.jetpackcomposetips.ui.swipe_to_dismiss

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun SwipeItemToDismiss(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit = {},
    backgroundContent: @Composable RowScope.() -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "offsetTransition")
    val dismissThreshold = 200f

    Box(modifier = modifier.wrapContentSize()) {
        Row(
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.CenterEnd)
                .background(Color.Red),
            content = backgroundContent
        )

        Row(
            modifier = Modifier
                .offset(x = animatedOffsetX.dp, y = 0.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount
                            if (offsetX < 0f) offsetX = 0f
                        },
                        onDragEnd = {
                            if (offsetX < -dismissThreshold) {
                                offsetX = 500f
                                onSwipeLeft()
                            } else
                                offsetX = 0f
                        }
                    )
                },
            content = content
        )
    }
}
