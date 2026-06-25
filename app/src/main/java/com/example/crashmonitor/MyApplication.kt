package com.example.crashmonitor

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        CrashMonitor.init(
            application = this,
            appId = "my-app-123",
            serverUrl = "https://your-backend.com"
        )
    }
}