package com.example.crashmonitor

import android.app.Activity
import android.app.Application
import android.os.Bundle

internal object ScreenTracker : Application.ActivityLifecycleCallbacks {

    var currentScreen: String = "Unknown"

    fun register(app: Application) {
        app.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityResumed(activity: Activity) {
        currentScreen = activity.javaClass.simpleName
    }

    // Other lifecycle methods - empty
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}