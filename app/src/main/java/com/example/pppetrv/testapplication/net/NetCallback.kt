package com.example.pppetrv.testapplication.net

interface NetCallback<T> {
    fun onSuccess(result: T)
    fun onError(e: Throwable)
}