package com.example.corp.base

interface CoroutineUseCase<Result> {
    suspend fun run(): Result
}
