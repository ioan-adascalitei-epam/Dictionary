package com.example.dictionary.util

import com.example.dictionary.data.model.ErrorInfo

sealed class Resource<T>(
    val data: T?,
    val error: ErrorInfo?
) {
    data class Success<T>(val info: T) : Resource<T>(info, null)
    data class Error<T>(val errorInfo: ErrorInfo?) : Resource<T>(null, errorInfo)
}