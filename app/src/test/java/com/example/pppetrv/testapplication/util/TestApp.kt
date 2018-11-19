package com.example.pppetrv.testapplication.util

import com.example.pppetrv.testapplication.App
import com.example.pppetrv.testapplication.dagger.components.AppComponent
import com.example.pppetrv.testapplication.dagger.components.DaggerTestAppComponent
import com.example.pppetrv.testapplication.dagger.modules.TestAppModule

class TestApp: App() {
    override fun buildComponent(): AppComponent {
        val component = DaggerTestAppComponent.builder()
                .testAppModule(TestAppModule())
                .build()
        (component as DaggerTestAppComponent).inject(this)
        return component
    }
}