package com.example.corp.common

interface Reducer<S, E : StateEvent> {

    fun reduce(state: S, event: E): S
}
