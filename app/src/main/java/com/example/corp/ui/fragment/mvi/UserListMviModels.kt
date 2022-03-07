package com.example.corp.ui.fragment.mvi


import com.example.corp.common.Person
import com.example.corp.common.StateEvent
import com.example.corp.common.ViewIntent

sealed class UserListViewIntent : ViewIntent {
    object Initial : UserListViewIntent()
    object Retry : UserListViewIntent()
}

sealed class UserListEvent : StateEvent {
    object UserListLoading : UserListEvent()
    class UserListLoaded(val data: List<Person>) : UserListEvent()
    class UserListLoadingFailed(val message: String) : UserListEvent()
}

