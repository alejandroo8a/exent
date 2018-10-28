package com.example.alien.excent.network

import com.example.alien.excent.data.NetworkResult
import retrofit2.HttpException
import javax.inject.Inject
import timber.log.Timber
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.UnknownHostException

class ResponseConversions @Inject
internal constructor() {
    private val HTTP_REQUEST_TIMEOUT = 408
    private val HTTP_UNAUTHORIZED = 401
    private val HTTP_FORBIDDEN = 403
    private val HTTP_NOT_FOUND = 404

    private fun isConnectionException(throwable: Throwable): Boolean {
        return (throwable is HttpException && isConnectionException(throwable as HttpException)
                || throwable is UnknownHostException
                || throwable is InterruptedIOException
                || throwable is SocketException)
    }

    private fun isConnectionException(httpException: HttpException): Boolean {
        return httpException.code() == HTTP_REQUEST_TIMEOUT
    }

    private fun isAuthorizationException(throwable: Throwable): Boolean {
        return throwable is HttpException && (throwable as HttpException).code() == HTTP_UNAUTHORIZED
    }

    private fun isForbiddenException(throwable: Throwable): Boolean {
        return throwable is HttpException && (throwable as HttpException).code() == HTTP_FORBIDDEN
    }

    private fun isNotFoundException(throwable: Throwable): Boolean {
        return throwable is HttpException && (throwable as HttpException).code() == HTTP_NOT_FOUND
    }

    fun toNetworkResult(throwable: Throwable): NetworkResult {
        Timber.d("Converting to NetworkResult: %s", throwable.message)
        return when {
            isConnectionException(throwable) -> NetworkResult.CONNECTION_ERROR
            isAuthorizationException(throwable) -> NetworkResult.AUTHORIZATION_ERROR
            isForbiddenException(throwable) -> NetworkResult.FORBIDDEN_ERROR
            isNotFoundException(throwable) -> NetworkResult.NOT_FOUND_ERROR
            else -> NetworkResult.GENERIC_ERROR
        }
    }
}