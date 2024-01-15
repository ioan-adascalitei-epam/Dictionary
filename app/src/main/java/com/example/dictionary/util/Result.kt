package com.example.dictionary.util

import com.example.dictionary.domain.model.ErrorInfo

sealed class Result<T>(
    val data: T?,
    val error: ErrorInfo?
) {
    data class Success<T>(val info: T) : Result<T>(info, null)
    data class Error<T>(val errorInfo: ErrorInfo?) : Result<T>(null, errorInfo)
}