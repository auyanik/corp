package com.example.corp.ui.fragment.mvi

import com.example.corp.common.FetchError
import com.example.corp.common.Person

data class UserListState(
    val isLoading: Boolean,
    val loadingError: FetchError?,
    val personList: List<Person>?
) {
    companion object {
        fun initial() = UserListState(
            isLoading = true,
            loadingError = null,
            personList = emptyList()
        )
    }
}
