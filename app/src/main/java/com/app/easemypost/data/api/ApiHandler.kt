package com.app.easemypost.data.api

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

sealed class ApiHandler<out T> {

    data class Success<out T>(val data: T) : ApiHandler<T>()
    data class Error(val exception: Throwable, val errorMessage: String?,val errorCode: Int) : ApiHandler<Nothing>()
    object Loading : ApiHandler<Nothing>()

    companion object {
        suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>): ApiHandler<T> {
            return try {
                val response = apiCall()
                if (response.isSuccessful) {
                    Success(response.body()!!)
                } else {
                    // Parse the error body to extract the message
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = errorBody?.let {
                        try {
                            // Parse the JSON to get the "message"
                            val json = JSONObject(it)
                            json.getString("message") // Extract "message" from JSON
                        } catch (e: JSONException) {
                            null // Fallback if JSON parsing fails
                        }
                    } ?: "Unknown error"

                    Error(
                        exception = Throwable(errorMessage),
                        errorMessage = errorMessage,
                        errorCode = response.code()
                    )
                }
            } catch (exception: Exception) {
                // Handle exceptions (network errors, etc.)
                Error(
                    exception = exception,
                    errorMessage = exception.localizedMessage ?: "An unexpected error occurred",
                    errorCode = 0
                )
            }
        }
    }
}
