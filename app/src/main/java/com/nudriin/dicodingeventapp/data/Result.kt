package com.nudriin.dicodingeventapp.data

import com.nudriin.dicodingeventapp.util.Event

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Event<String>) : Result<Nothing>()
    object Loading : Result<Nothing>()
}