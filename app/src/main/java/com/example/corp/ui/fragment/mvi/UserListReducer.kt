package com.example.corp.ui.fragment.mvi

import com.example.corp.common.FetchError
import com.example.corp.common.Reducer
import com.example.corp.ui.fragment.mvi.UserListEvent.*


class UserListReducer : Reducer<UserListState, UserListEvent> {

    override fun reduce(
        state: UserListState,
        event: UserListEvent
    ): UserListState {
        return when (event) {
            is UserListLoaded -> userListLoaded(state, event)
            is UserListLoadingFailed -> userListFailed(state, event)
            is UserListLoading -> userListLoading(state)
        }
    }

    private fun userListLoading(
        state: UserListState
    ) = state.copy(
        isLoading = true,
        loadingError = null
    )

    private fun userListLoaded(
        state: UserListState,
        event: UserListLoaded
    ): UserListState = state.copy(
        isLoading = false,
        loadingError = null,
        personList = event.data
    )

    private fun userListFailed(
        state: UserListState,
        event: UserListLoadingFailed
    ) = state.copy(
        isLoading = false,
        loadingError = FetchError(
            errorDescription = event.message
        )
    )
}
