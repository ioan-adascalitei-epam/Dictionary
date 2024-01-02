package com.example.dictionary.data.model

sealed class ErrorInfo {
    data class GenericInfo(val msg: String): ErrorInfo()
    data class NoInternet(val msg: String): ErrorInfo()
    data class NotFound(val msg: String): ErrorInfo()
    data class AudioPlayError(val msg: String = ""): ErrorInfo()
}