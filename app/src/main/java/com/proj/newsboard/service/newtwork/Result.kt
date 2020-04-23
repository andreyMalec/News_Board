package com.proj.newsboard.service.newtwork

sealed class Result<out T> {
    data class Success<out T>(val value: T): Result<T>()
    data class Failure(val t: Throwable): Result<Nothing>()
}