package com.example.rickandmortyapp.util

import com.example.rickandmortyapp.model.remote.NetworkResponse
import retrofit2.Response

open class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): NetworkResponse<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    return NetworkResponse.success(it)
                }
            }
            return error(response.message())
        } catch (err: Exception) {
            return error(err.message.toString())
        }
    }

    private fun <T> error(message: String) =
        NetworkResponse.error<T>(exception = null, message = "Network error: $message")

}