package com.example.crashmonitor

import com.example.crashmonitor.model.ApiFailureEvent
import com.example.crashmonitor.model.CrashEvent
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

internal object NetworkManager {

    private val client = OkHttpClient()
    private val gson = Gson()
    private val JSON = "application/json".toMediaType()
    private val scope = CoroutineScope(Dispatchers.IO)

    private var serverUrl: String = ""

    fun init(url: String) {
        serverUrl = url
    }

    fun sendCrash(event: CrashEvent) {
        scope.launch {
            try {
                val json = gson.toJson(event)
                val body = json.toRequestBody(JSON)
                val request = Request.Builder()
                    .url("$serverUrl/crash")
                    .post(body)
                    .build()
                client.newCall(request).execute()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun sendApiFailure(event: ApiFailureEvent) {
        scope.launch {
            try {
                val json = gson.toJson(event)
                val body = json.toRequestBody(JSON)
                val request = Request.Builder()
                    .url("$serverUrl/api-failure")
                    .post(body)
                    .build()
                client.newCall(request).execute()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}