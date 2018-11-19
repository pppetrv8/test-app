package com.example.pppetrv.testapplication.util

import com.example.pppetrv.testapplication.extension.notNull
import java.util.*

class ScheduleTimer(private var timerPeriod: Long) {

    private var timer: Timer? = null
    private var listener: TimerTaskListener? = null

    interface TimerTaskListener {
        fun onTimer()
    }

    fun setOnTimerTaskListener(listener: TimerTaskListener) {
        this.listener = listener
    }

    fun setTimerPeriod(timerPeriod: Long) {
        this.timerPeriod = timerPeriod
    }

    private fun schedule() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (listener != null) {
                    listener?.onTimer()
                }
            }
        }, 0, timerPeriod)
    }

    fun start() {
        stop()
        schedule()
    }

    fun stop() {
        timer.notNull {
            it.purge()
            it.cancel()
            timer = null
        }
    }
}
