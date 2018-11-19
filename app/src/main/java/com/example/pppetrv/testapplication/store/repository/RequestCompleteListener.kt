package com.example.pppetrv.testapplication.store.repository

interface RequestCompleteListener<T> {
    fun onSuccess(result: T)
    fun onError(e: Throwable)
}