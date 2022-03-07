package com.example.corp.base

interface CoroutineUseCase<Params, Result> {
    suspend fun run(params: Params): Result
}
