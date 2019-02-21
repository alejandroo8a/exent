package com.example.alien.excent.data

class ResultData<T> {

    val networkResult: NetworkResult
    val data: T?

    val isSuccessful: Boolean
        get() = networkResult === NetworkResult.SUCCESS

    constructor(data: T) {
        this.data = data
        this.networkResult = NetworkResult.SUCCESS
    }

    constructor(networkResult: NetworkResult) {
        if (networkResult === NetworkResult.SUCCESS) {
            throw IllegalStateException("Successful result should have accompanying data.")
        }

        this.data = null
        this.networkResult = networkResult
    }
}
