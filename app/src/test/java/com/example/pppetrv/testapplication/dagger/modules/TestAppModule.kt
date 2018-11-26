package com.example.pppetrv.testapplication.dagger.modules

import com.example.pppetrv.testapplication.net.NetService
import com.example.pppetrv.testapplication.util.rx.SchedulerProvider
import com.example.pppetrv.testapplication.store.db.DbProvider
import com.example.pppetrv.testapplication.store.repository.AppRepository
import com.example.pppetrv.testapplication.store.repository.Repository
import com.example.pppetrv.testapplication.util.db.TestRoomDbProvider
import com.example.pppetrv.testapplication.util.net.TestNetService
import com.example.pppetrv.testapplication.util.rx.TestSchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
class TestAppModule {

    @Provides
    @Singleton
    fun provideDbProvider(): DbProvider {
        return TestRoomDbProvider()
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