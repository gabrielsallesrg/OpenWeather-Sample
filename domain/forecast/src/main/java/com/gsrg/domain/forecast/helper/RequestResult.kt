package com.gsrg.domain.forecast.helper

sealed class RequestResult<out T: Any> {

    data class Success<out D: Any>(val data: D?): RequestResult<D>()
    data class Error(val exception: Exception): RequestResult<Nothing>()
}
