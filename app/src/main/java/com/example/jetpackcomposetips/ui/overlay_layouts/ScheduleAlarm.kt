package com.example.jetpackcomposetips.ui.overlay_layouts

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import androidx.core.net.toUri

@Composable
fun ScheduleAlarm(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val requestOverlayPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (!Settings.canDrawOverlays(context)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                "package:${context.packageName}".toUri()
            )
            context.startActivity(intent)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(onClick = {
            handleAlarmPermission(
                context = context,
                requestOverlayPermission = {
                    requestOverlayPermission(
                        context,
                        requestOverlayPermission
                    )
                })
        }) {
            Text("Schedule Alarm")
        }
    }
}


fun handleAlarmPermission(
    context: Context,
    requestOverlayPermission: () -> Unit,
) {
    with(context) {
        if (checkScheduleAlarmPermission()) {
            if (Settings.canDrawOverlays(this))
                setAlarm(this)
            else requestOverlayPermission()
        } else requestScheduleAlarmPermission()
    }
}

fun setAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val triggerTime = System.currentTimeMillis() + 3 * 60 * 1000
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        if (alarmManager.canScheduleExactAlarms())
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
}

fun Context.checkScheduleAlarmPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = this.getSystemService<AlarmManager>() as AlarmManager
        return alarmManager.canScheduleExactAlarms()
    } else true
}

fun Context.requestScheduleAlarmPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
        this.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
}

fun requestOverlayPermission(
    context: Context,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>,
) {
    val intent = Intent(
        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
        "package:${context.packageName}".toUri()
    )
    launcher.launch(intent)
}