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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SwipeItemToDismiss(
    modifier: Modifier = Modifier,
    enableGesture: Boolean = true,
    enableSwipeRight: Boolean = true,
    enableSwipeLeft: Boolean = true,
    colors: SwipeItemColors = DefaultSwipeItemToDismiss.colors(),
    onSwipeRight: () -> Unit = {},
    onSwipeLeft: () -> Unit = {},
    backgroundContent: @Composable RowScope.() -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(targetValue = offsetX, label = "offsetTransition")
    val scope = rememberCoroutineScope()
    val dismissThreshold = (LocalConfiguration.current.screenWidthDp / 3).toFloat()
    val exitScreenWidth = (LocalConfiguration.current.screenWidthDp * 2).toFloat()

    Box(modifier = modifier.wrapContentSize()) {

        if (offsetX < 0)
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .align(Alignment.CenterEnd)
                    .background(colors.containerRightColor),
                content = backgroundContent
            )

        if (offsetX > 0)
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .align(Alignment.CenterStart)
                    .background(colors.containerLeftColor),
                content = backgroundContent
            )

        Row(
            modifier = Modifier
                .offset(x = animatedOffsetX.dp, y = 0.dp)
                .pointerInput(Unit) {
                    if (enableGesture)
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()
                                if ((dragAmount > 0 && enableSwipeRight) || (dragAmount < 0 && enableSwipeLeft))
                                    offsetX += dragAmount
                            },
                            onDragEnd = {
                                when {
                                    offsetX > dismissThreshold && enableSwipeRight -> {
                                        scope.launch {
                                            offsetX = exitScreenWidth
                                            onSwipeRight()
                                            delay(200)
                                            offsetX = 0f
                                        }
                                    }

                                    offsetX < -dismissThreshold && enableSwipeLeft -> {
                                        scope.launch {
                                            offsetX = -exitScreenWidth
                                            onSwipeLeft()
                                            delay(200)
                                            offsetX = 0f
                                        }
                                    }

                                    else -> scope.launch { offsetX = 0f }
                                }
                            }
                        )
                },
            content = content
        )
    }
}
