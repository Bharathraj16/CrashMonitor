package com.example.crashmonitor.model

data class ApiFailureEvent(
    val appId: String,
    val screenName: String,
    val url: String,
    val statusCode: Int,
    val errorBody: String?,
    val method: String,
    val timestamp: Long = System.currentTimeMillis()
)