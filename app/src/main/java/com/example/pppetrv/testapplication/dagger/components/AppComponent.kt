package com.example.pppetrv.testapplication.dagger.components

import com.example.pppetrv.testapplication.App
import com.example.pppetrv.testapplication.dagger.modules.AppModule
import com.example.pppetrv.testapplication.ui.model.factory.BaseViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)
    fun inject(factory: BaseViewModelFactory)
}