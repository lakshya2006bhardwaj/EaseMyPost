package com.app.easemypost.data.api

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
                    Error(Throwable("Error: ${response.message()}"), response.message(),response.code())
                }
            } catch (exception: Exception) {
                // Handle all exceptions (network error, unexpected error, etc.)
                Error(exception, exception.localizedMessage, 0)
            }
        }
    }
}
