package com.example.corp.ui.fragment

import com.example.corp.base.BaseViewModel
import com.example.corp.common.Person
import com.example.corp.common.ViewState
import com.example.corp.domain.UserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@FlowPreview
@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userListUseCase: UserListUseCase
) : BaseViewModel() {

    private val mutableUserList = MutableStateFlow<ViewState<List<Person>>>(ViewState.Empty)
    val userList: StateFlow<ViewState<List<Person>>> = mutableUserList

    suspend fun getPersons(page: Int) = runCatching {
        mutableUserList.value = ViewState.Loading
        userListUseCase.run(page)
    }.fold(
        onSuccess = {
            mutableUserList.value = ViewState.Loaded(it.fetchResponse?.people.orEmpty())
        },
        onFailure = {
            mutableUserList.value = ViewState.Failure(it.localizedMessage.orEmpty())
        }
    )
}
