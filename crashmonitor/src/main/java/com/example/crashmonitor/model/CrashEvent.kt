package com.example.crashmonitor.model

data class CrashEvent(
    val appId: String,
    val screenName: String,
    val errorMessage: String,
    val stackTrace: String,
    val deviceModel: String = android.os.Build.MODEL,
    val osVersion: String = android.os.Build.VERSION.RELEASE,
    val timestamp: Long = System.currentTimeMillis()
)