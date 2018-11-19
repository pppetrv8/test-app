package com.example.pppetrv.testapplication

import android.app.Application
import com.example.pppetrv.testapplication.dagger.components.AppComponent
import com.example.pppetrv.testapplication.dagger.components.DaggerAppComponent
import com.example.pppetrv.testapplication.dagger.modules.AppModule
import com.example.pppetrv.testapplication.store.repository.Repository
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class App : Application() {

    companion object {
        @JvmStatic var appInstance: App? = null
        @JvmStatic var component: AppComponent? = null
    }

    @Inject
    lateinit var repository: Repository

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        component = buildComponent()
        //Realm.init(this)
    }

    open fun buildComponent(): AppComponent {
        val component = DaggerAppComponent.builder()
                .appModule(AppModule())
                .build()
        (component as DaggerAppComponent).inject(this)
        return component
    }
}