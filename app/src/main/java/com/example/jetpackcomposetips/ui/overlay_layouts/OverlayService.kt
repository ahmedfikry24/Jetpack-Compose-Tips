package com.example.jetpackcomposetips.ui.overlay_layouts

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.jetpackcomposetips.R

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View


    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        setupOverlay()
    }


    private fun setupOverlay() {
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null)
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 0
            y = 100
        }

        windowManager.addView(overlayView, layoutParams)

        overlayView.findViewById<TextView>(R.id.test).setOnClickListener {
            overlayView.isVisible = false
        }
    }


    override fun onBind(p0: Intent?): IBinder? = null
}
