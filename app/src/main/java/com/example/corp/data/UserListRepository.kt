package com.example.corp.data

import com.example.corp.common.DataSource
import com.example.corp.common.FetchError
import com.example.corp.common.FetchResponse
import javax.inject.Inject

class UserListRepository @Inject constructor(private val dataSource: DataSource) {
    suspend fun getPersons(): DataSourceResponse {
        return dataSource.fetch(next = null)
    }
}

data class DataSourceResponse(
    val fetchResponse: FetchResponse?,
    val fetchError: FetchError?
)
