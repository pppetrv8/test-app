package com.example.pppetrv.testapplication.dagger.modules

import android.os.Looper
import android.support.annotation.MainThread
import com.example.pppetrv.testapplication.net.NetService
import com.example.pppetrv.testapplication.util.rx.SchedulerProvider
import com.example.pppetrv.testapplication.store.db.DbProvider
import com.example.pppetrv.testapplication.store.repository.AppRepository
import com.example.pppetrv.testapplication.store.repository.Repository
import com.example.pppetrv.testapplication.util.Constants
import com.example.pppetrv.testapplication.util.net.TestNetService
import com.example.pppetrv.testapplication.util.rx.TestSchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun provideDbProvider(): DbProvider {
        return DbProvider.getDbProvider(Constants.DB_TYPE)
    }

    @Provides
    @Singleton
    fun provideNetService(): NetService {
        return TestNetService()
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider {
        return TestSchedulerProvider(Schedulers.trampoline())
    }

    @Provides
    @Singleton
    fun provideRepository(repository: AppRepository): Repository {
        return repository
    }
}