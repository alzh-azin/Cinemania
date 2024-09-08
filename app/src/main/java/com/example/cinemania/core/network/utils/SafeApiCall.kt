package com.example.cinemania.core.network.utils

import org.json.JSONObject
import retrofit2.Response

suspend fun <T> safeApiCall(
    call: suspend () -> Response<T>,
    exceptionMessage: String
): NetworkResult<T> {
    return try {
        val response = call()

        if (response.isSuccessful) {
            val body = response.body()
            NetworkResult.Success(body)

        } else {

            val error = errorParser(response.errorBody()?.string())
            NetworkResult.Error(error.code, error.message.orEmpty())
        }
    } catch (e: Exception) {
        NetworkResult.Error(errorMessage = exceptionMessage)
    }
}

fun errorParser(errorBody: String?): CinemaniaError {
    try {
        return if (errorBody != null) {

            val errorBodyObject = JSONObject(errorBody)

            val errorCode = if (errorBodyObject.has("error")) {
                if (errorBodyObject.getJSONObject("error").has("code"))
                    errorBodyObject.getJSONObject("error").getString("code")
                else ""
            } else ""

            val errorMessage = errorBodyObject.getJSONObject("error").toString()

            CinemaniaError(
                code = errorCode,
                message = errorMessage
            )
        } else {
            CinemaniaError()
        }
    } catch (e: Exception) {
        return CinemaniaError()
    }
}

