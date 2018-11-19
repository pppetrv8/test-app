package com.example.pppetrv.testapplication.dagger.components

import com.example.pppetrv.testapplication.RepositoryUnitTest
import com.example.pppetrv.testapplication.dagger.modules.TestAppModule
import com.example.pppetrv.testapplication.util.TestApp
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent : AppComponent {
    fun inject(app: TestApp)
    fun inject(test: RepositoryUnitTest)
}