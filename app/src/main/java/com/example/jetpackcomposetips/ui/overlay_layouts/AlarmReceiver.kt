package com.example.jetpackcomposetips.ui.overlay_layouts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.let { context ->
            val intent = Intent(context, OverlayService::class.java)
            context.startService(intent)
        }
    }
}
