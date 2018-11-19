package com.example.pppetrv.testapplication.store.db.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.example.pppetrv.testapplication.App
import com.example.pppetrv.testapplication.store.db.sqlite.dao.BankDepartmentDAO
import com.example.pppetrv.testapplication.store.db.sqlite.dao.CurrencyRatesDAO

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        const val DELETE_FROM_SQLITE_SEQUENCE = "DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"
        const val ALTER_TABLE = "ALTER TABLE "
        const val ADD_COLUMN = " ADD COLUMN "

        const val TABLE_CUR_RATES = "cur_rates"
        const val TABLE_BANK_DEPS = "bank_deps"

        const val DATABASE_NAME = "test.db"
        const val DATABASE_VERSION = 2

        var helperInstance: DataBaseHelper? = null

        fun getInstance(): DataBaseHelper? {
            if (helperInstance == null) {
                helperInstance = DataBaseHelper(App.appInstance as Context)
            }
            return helperInstance
        }
    }

    @Synchronized
    override fun close() {
        super.close()
    }

    override fun onCreate(database: SQLiteDatabase) {
        Thread { createDbTables(database) }.start()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Thread {
            dropDbTables(db)
            createDbTables(db)
        }.start()
    }

    @Synchronized
    private fun createDbTables(db: SQLiteDatabase) {
        db.execSQL(CurrencyRatesDAO.SQL_CREATE_TABLE_CUR_RATES)
        db.execSQL(BankDepartmentDAO.SQL_CREATE_TABLE_BANKS)
    }

    @Synchronized
    private fun dropDbTables(db: SQLiteDatabase) {
        db.execSQL(CurrencyRatesDAO.SQL_DROP_TABLE_CUR_RATES)
        db.execSQL(BankDepartmentDAO.SQL_DROP_TABLE_BANKS)
    }
}
