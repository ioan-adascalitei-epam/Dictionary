package com.example.dictionary.domain.model

sealed class ErrorInfo(val message: String) {
    data class GenericInfo(val msg: String): ErrorInfo(msg)
    data class NoInternet(val msg: String): ErrorInfo(msg)
    data class NotFound(val msg: String): ErrorInfo(msg)
}