package com.example.pppetrv.testapplication.store.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.pppetrv.testapplication.store.db.room.dao.BankDepartmentDao

import com.example.pppetrv.testapplication.store.db.room.dao.CurrencyRateDao
import com.example.pppetrv.testapplication.store.db.room.entity.BankDepartmentEntity
import com.example.pppetrv.testapplication.store.db.room.entity.CurrencyRateEntity

@Database(entities = [
    CurrencyRateEntity::class,
    BankDepartmentEntity::class], version = 1)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract val currencyRateDao: CurrencyRateDao
    abstract val bankDepartmentDao: BankDepartmentDao
}
