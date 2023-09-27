package com.rahultyagi.util

import io.ktor.http.*

sealed class Response<T>(val code: HttpStatusCode = HttpStatusCode.OK, val response: T) {
    class Success<T>(data: T) : Response<T>(response = data)
    class Error<T>(code: HttpStatusCode, data: T) : Response<T>(code = code, response = data)
}
