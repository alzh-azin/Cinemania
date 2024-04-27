package com.example.cinemania.core.network.utils

sealed class NetworkResult<out T> {
    class Loading<out T>(isLoading: Boolean) : NetworkResult<T>()
    class Success<out T>(val data: T?) : NetworkResult<T>()
    class Error<out T>(val code: String? = null, val errorMessage: String?) : NetworkResult<T>()
    class Exception<out T>(val exceptionMessage: String?) : NetworkResult<T>()
}