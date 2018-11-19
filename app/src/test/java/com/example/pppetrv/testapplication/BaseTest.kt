package com.example.pppetrv.testapplication

import com.example.pppetrv.testapplication.util.TestApp
import com.example.pppetrv.testapplication.dagger.components.TestAppComponent
import org.junit.Before
import org.junit.Ignore
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, constants = BuildConfig::class, sdk = [21])
@Ignore
open class BaseTest {

    var component: TestAppComponent? = null

    @Before
    @Throws(Exception::class)
    open fun setUp() {
        component = App.component as TestAppComponent
    }

}