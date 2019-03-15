package com.example.pppetrv.testapplication.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MyActivity : AppCompatActivity(), CoroutineScope {

    lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Unconfined + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel() // Cancel job on activity destroy. After destroy all children jobs will be cancelled automatically
    }

    /*
     * Note how coroutine builders are scoped: if activity is destroyed or any of the launched coroutines
     * in this method throws an exception, then all nested coroutines are cancelled.
     */
    fun loadDataFromUI() = launch { // <- extension on current activity, launched in the main thread
        val ioData = async(Dispatchers.IO) { // <- extension on launch scope, launched in IO dispatcher
            // blocking I/O operation
        }
        // do something else concurrently with I/O
        val data = ioData.await() // wait for result of I/O
        draw(data) // can draw in the main thread
    }

    private fun draw(data: Any) {

    }
}