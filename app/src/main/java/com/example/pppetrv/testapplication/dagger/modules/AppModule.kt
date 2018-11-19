package com.example.pppetrv.testapplication.dagger.modules

import com.example.pppetrv.testapplication.net.NetService
import com.example.pppetrv.testapplication.util.rx.AppSchedulerProvider
import com.example.pppetrv.testapplication.util.rx.SchedulerProvider
import com.example.pppetrv.testapplication.store.db.DbProvider
import com.example.pppetrv.testapplication.store.repository.AppRepository
import com.example.pppetrv.testapplication.store.repository.Repository
import com.example.pppetrv.testapplication.util.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideDbProvider(): DbProvider {
        return DbProvider.getDbProvider(Constants.DB_TYPE)
    }

    @Provides
    @Singleton
    fun provideNetService(): NetService {
        return NetService()
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideRepository(repository: AppRepository): Repository {
        return repository
    }
}