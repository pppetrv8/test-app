package com.example.pppetrv.testapplication.util.db

import android.arch.persistence.room.Room
import android.content.Context
import com.example.pppetrv.testapplication.App
import com.example.pppetrv.testapplication.store.db.room.AppDatabase
import com.example.pppetrv.testapplication.store.db.room.RoomDbProvider
import com.example.pppetrv.testapplication.BuildConfig
import com.example.pppetrv.testapplication.util.TestApp
import org.junit.Before
import org.junit.Ignore
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = TestApp::class, constants = BuildConfig::class, sdk = [21])
@Ignore
class TestRoomDbProvider: RoomDbProvider() {

    @Before
    override fun initAppDb() {
        println("init Room DB")
        appDB = Room.inMemoryDatabaseBuilder(App.appInstance as Context, AppDatabase::class.java)
                .allowMainThreadQueries().build()
    }
}
