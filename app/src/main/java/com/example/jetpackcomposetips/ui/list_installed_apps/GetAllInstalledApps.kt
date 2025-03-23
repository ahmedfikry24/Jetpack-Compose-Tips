package com.example.jetpackcomposetips.ui.list_installed_apps

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

fun getAllInstalledApps(context: Context): List<ApplicationInfo> {
    val packageManager = context.packageManager
    return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        .filter { app -> (app.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }
}
