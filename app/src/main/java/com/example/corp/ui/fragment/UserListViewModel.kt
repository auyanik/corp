package com.example.corp.ui.fragment

import androidx.lifecycle.viewModelScope
import com.example.corp.base.BaseViewModel
import com.example.corp.domain.UserListUseCase
import com.example.corp.ui.fragment.mvi.UserListReducer
import com.example.corp.ui.fragment.mvi.UserListEvent
import com.example.corp.ui.fragment.mvi.UserListState
import com.example.corp.ui.fragment.mvi.UserListViewIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@FlowPreview
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase
) : BaseViewModel() {

    private val intentMutableFlow = MutableSharedFlow<UserListViewIntent>(replay = 1)

    val state: StateFlow<UserListState> = intentMutableFlow
        .toEvents()
        .reduce()
        .stateIn(viewModelScope, SharingStarted.Lazily, UserListState.initial())

    suspend fun processViewIntent(intent: UserListViewIntent) = intentMutableFlow.emit(intent)

    private fun Flow<UserListViewIntent>.toEvents() = flatMapConcat {
        when (it) {
            UserListViewIntent.Initial -> personsInitial()
            UserListViewIntent.Retry -> personsInitial()
        }
    }

    private fun Flow<UserListEvent>.reduce() = map {
        UserListReducer().reduce(state.value, it)
    }

    private suspend fun personsInitial() = flowOf(getPersons())

    private suspend fun getPersons(): UserListEvent = runCatching {
        userListUseCase.run()
    }.fold(
        onSuccess = {
            UserListEvent.UserListLoaded(it.fetchResponse?.people.orEmpty())
        },
        onFailure = {
            UserListEvent.UserListLoadingFailed(it.localizedMessage.orEmpty())
        }
    )
}
