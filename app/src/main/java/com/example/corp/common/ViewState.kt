package com.example.corp.common

sealed class ViewState<out T> {
    object Loading : ViewState<Nothing>()
    object Empty : ViewState<Nothing>()
    data class Loaded<out T>(val data: T) : ViewState<T>()
    data class Failure(val errorMessage: String?) : ViewState<Nothing>()
}
