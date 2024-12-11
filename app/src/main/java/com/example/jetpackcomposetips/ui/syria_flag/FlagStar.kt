package com.example.jetpackcomposetips.ui.syria_flag

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun FlagStar(
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2, size.height / 2)
        val outerRadius = size.minDimension / 3
        val innerRadius = outerRadius / 2.5f
        val path = Path()

        for (i in 0 until 5) {
            val outerAngle = (i * 2 * PI) / 5 - PI / 2
            val outerX = center.x + outerRadius * cos(outerAngle).toFloat()
            val outerY = center.y + outerRadius * sin(outerAngle).toFloat()

            if (i == 0) path.moveTo(outerX, outerY)
            else path.lineTo(outerX, outerY)

            val innerAngle = outerAngle + PI / 5
            val innerX = center.x + innerRadius * cos(innerAngle).toFloat()
            val innerY = center.y + innerRadius * sin(innerAngle).toFloat()

            path.lineTo(innerX, innerY)
        }


        path.close()
        drawPath(path = path, color = color)
    }
}


@Preview(showBackground = true)
@Composable
private fun FlagStarPreview() {
    FlagStar()
}