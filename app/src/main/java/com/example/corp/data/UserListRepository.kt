package com.example.corp.data

import com.example.corp.common.DataSource
import com.example.corp.common.FetchError
import com.example.corp.common.FetchResponse
import javax.inject.Inject

class UserListRepository @Inject constructor(private val dataSource: DataSource) {
    suspend fun getPersons(next: Int): DataSourceResponse {
        return dataSource.fetch(next = next.toString())
    }
}

data class DataSourceResponse(
    val fetchResponse: FetchResponse?,
    val fetchError: FetchError?
)
