package com.example.corp.domain

import com.example.corp.base.CoroutineUseCase
import com.example.corp.data.DataSourceResponse
import com.example.corp.data.UserListRepository
import javax.inject.Inject

class UserListUseCase @Inject constructor(private val userListRepository: UserListRepository) :
    CoroutineUseCase<DataSourceResponse> {
    override suspend fun run(): DataSourceResponse {
        return userListRepository.getPersons()
    }
}
