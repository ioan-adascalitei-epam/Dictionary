package com.example.dictionary.data.datasource.remote

import com.example.dictionary.domain.model.ErrorInfo
import com.example.dictionary.util.Result
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

suspend fun <T: Any> apiCall(call: suspend () -> Response<T>): Result<T> = try {
    call().run {
        if (isSuccessful) {
            body()?.let {
                Result.Success(body()!!)
            } ?: Result.Error(ErrorInfo.GenericInfo(errorBody()?.string().orEmpty()))
        } else Result.Error(ErrorInfo.GenericInfo(this.raw().message()))
    }
} catch (e: Exception) {
    Result.Error(mapError(e))
}

private fun mapError(e: Exception): ErrorInfo = when (e) {
    is HttpException -> {
        if (e.code() == 404) {
            ErrorInfo.NotFound(e.message())
        } else {
            ErrorInfo.GenericInfo(e.message())
        }
    }

    is UnknownHostException -> ErrorInfo.NoInternet(e.message.orEmpty())
    else -> ErrorInfo.GenericInfo(e.message.orEmpty())
}
