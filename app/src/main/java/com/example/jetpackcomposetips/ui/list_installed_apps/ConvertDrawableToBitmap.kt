package com.example.jetpackcomposetips.ui.list_installed_apps

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable

fun convertDrawableToBitmap(drawable: Drawable): Bitmap {
    return when (drawable) {
        is AdaptiveIconDrawable -> {
            if (drawable.background == null && drawable.foreground == null) {
                val width = drawable.intrinsicWidth
                val height = drawable.intrinsicHeight
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                    val canvas = Canvas(this)
                    drawable.setBounds(0, 0, width, height)
                    drawable.draw(canvas)
                }
            } else {
                val width = drawable.intrinsicWidth
                val height = drawable.intrinsicHeight
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                    val canvas = Canvas(this)
                    drawable.background?.setBounds(0, 0, width, height)
                    drawable.background?.draw(canvas)
                    drawable.foreground?.setBounds(0, 0, width, height)
                    drawable.foreground?.draw(canvas)
                }
            }
        }

        is VectorDrawable -> {
            val width = drawable.intrinsicWidth
            val height = drawable.intrinsicHeight
            Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
                val canvas = Canvas(this)
                drawable.setBounds(0, 0, width, height)
                drawable.draw(canvas)
            }
        }

        is BitmapDrawable -> drawable.bitmap

        else -> Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
    }
}
