package com.example.crashmonitor

import com.example.crashmonitor.model.ApiFailureEvent
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val appId: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return try {
            val response = chain.proceed(request)

            // API failure check - 4xx, 5xx errors
            if (!response.isSuccessful) {
                val errorBody = response.peekBody(2048).string()
                val event = ApiFailureEvent(
                    appId = appId,
                    screenName = ScreenTracker.currentScreen,
                    url = request.url.toString(),
                    statusCode = response.code,
                    errorBody = errorBody,
                    method = request.method
                )
                NetworkManager.sendApiFailure(event)
            }
            response

        } catch (e: Exception) {
            // Network error (no internet, timeout etc)
            val event = ApiFailureEvent(
                appId = appId,
                screenName = ScreenTracker.currentScreen,
                url = request.url.toString(),
                statusCode = -1, // -1 = network error
                errorBody = e.message,
                method = request.method
            )
            NetworkManager.sendApiFailure(event)
            throw e
        }
    }
}