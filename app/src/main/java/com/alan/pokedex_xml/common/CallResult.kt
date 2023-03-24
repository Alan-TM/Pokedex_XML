package com.alan.pokedex_xml.common

import java.lang.Exception

sealed class CallResult<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : CallResult<T>(data)
    class Error<T>(message: String, data: T? = null) : CallResult<T>(data, message)
    class Loading<T>(data: T? = null) : CallResult<T>(data)
    class ServerError<T>(error: Exception, code: Int? = null) : CallResult<T>()
    class SocketError<T>(error: Exception? = null) : CallResult<T>()
    class UnknownError<T>(error: Exception) : CallResult<T>()
}
