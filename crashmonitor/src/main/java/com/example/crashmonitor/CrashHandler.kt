package com.example.crashmonitor

import com.example.crashmonitor.model.CrashEvent

internal class CrashHandler(
    private val appId: String,
    private val defaultHandler: Thread.UncaughtExceptionHandler?
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            val event = CrashEvent(
                appId = appId,
                screenName = ScreenTracker.currentScreen,
                errorMessage = throwable.message ?: "Unknown Error",
                stackTrace = throwable.stackTraceToString()
            )
            // Sync call pannanum - app crash aagum munnaadi send pannanum
            NetworkManager.sendCrash(event)
            Thread.sleep(1500) // Server-ku reach aaga wait pannu
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // Default handler ku pass pannu (app crash normally)
        defaultHandler?.uncaughtException(thread, throwable)
    }
}