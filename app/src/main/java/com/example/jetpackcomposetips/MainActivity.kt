package com.example.jetpackcomposetips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.jetpackcomposetips.ui.overlay_layouts.ScheduleAlarm
import com.example.jetpackcomposetips.ui.theme.JetpackComposeTipsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeTipsTheme {
                ScheduleAlarm()
            }
        }
    }
}