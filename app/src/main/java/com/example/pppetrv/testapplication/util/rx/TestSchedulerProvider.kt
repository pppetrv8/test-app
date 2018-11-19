package com.example.pppetrv.testapplication.util.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

class TestSchedulerProvider(private val scheduler: Scheduler) : SchedulerProvider {

    override fun computation(): Scheduler {
        return scheduler
    }

    override fun io(): Scheduler {
        return scheduler
    }

    override fun ui(): Scheduler {
        return scheduler
    }
}
