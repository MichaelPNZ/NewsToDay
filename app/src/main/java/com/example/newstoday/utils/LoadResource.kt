package com.example.newstoday.utils

sealed class LoadResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : LoadResource<T>(data)
    class Error<T>(message: String, data: T? = null) : LoadResource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true) : LoadResource<T>(null)
}