package com.example.pppetrv.testapplication.util

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import timber.log.Timber

class ExecutorServiceManager {

    companion object {
        private const val AWAIT_TERMINATION_TIME = 10
    }

    private var executorService: ExecutorService? = null

    val isActive: Boolean
        get() = executorService != null && executorService?.isShutdown != true && executorService?.isTerminated != true

    fun runOperation(run: Runnable) {
        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor()
        }
        if (isActive) {
            executorService?.execute(run)
        }
    }

    fun shutdown() {
        if (!isActive) {
            return
        }
        executorService?.shutdown()
        try {
            executorService?.awaitTermination(AWAIT_TERMINATION_TIME.toLong(), TimeUnit.SECONDS)
        } catch (e: InterruptedException) {
            Timber.e(e)
        }
    }
}
