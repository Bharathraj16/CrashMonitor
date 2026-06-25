package com.example.crashmonitor

import android.app.Application

object CrashMonitor {

    private var appId: String = ""
    private var isInitialized = false

    fun init(
        application: Application,
        appId: String,
        serverUrl: String
    ) {
        if (isInitialized) return

        this.appId = appId

        // 1. Network manager setup
        NetworkManager.init(serverUrl)

        // 2. Screen tracker setup
        ScreenTracker.register(application)

        // 3. Crash handler setup
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(
            CrashHandler(appId, defaultHandler)
        )

        isInitialized = true
    }

    // Developer  expose the interceptor
    fun getApiInterceptor(): ApiInterceptor {
        check(isInitialized) {
            "CrashMonitor is not initialized. Please call CrashMonitor.init() in your Application's onCreate() before using it."
        }
        return ApiInterceptor(appId)
    }
}