package com.example.core.common.result

sealed interface NetworkResult<out T> {

    class Success<out T>(val data: T?) : NetworkResult<T>

    class Error<out T>(val code: String? = null, val errorMessage: String) : NetworkResult<T>

}