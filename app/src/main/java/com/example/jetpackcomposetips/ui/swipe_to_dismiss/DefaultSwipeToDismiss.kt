package com.example.jetpackcomposetips.ui.swipe_to_dismiss

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object DefaultSwipeItemToDismiss {

    @Composable
    fun colors(): SwipeItemColors {
        return SwipeItemColors(
            containerRightColor = MaterialTheme.colorScheme.error,
            containerLeftColor = MaterialTheme.colorScheme.errorContainer,
        )
    }
}

data class SwipeItemColors(
    val containerRightColor: Color,
    val containerLeftColor: Color,
)