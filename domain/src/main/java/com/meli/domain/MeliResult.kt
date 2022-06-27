package com.meli.domain

sealed interface MeliResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : MeliResult<T>
    data class Error(val message: String, val code: Int? = null) : MeliResult<Nothing>
}