package com.example.corp.ui.fragment.mvi

import com.example.corp.common.Person


sealed class UserListViewState {
    object Loading : UserListViewState()
    class Content(val personList: List<Person>) : UserListViewState()
    class Error(val errorMessage: String) : UserListViewState()

    companion object {
        fun fromState(state: UserListState): UserListViewState = when {
            state.isLoading -> Loading
            state.loadingError != null -> Error(state.loadingError.errorDescription)
            else -> Content(state.personList.orEmpty())
        }
    }
}

