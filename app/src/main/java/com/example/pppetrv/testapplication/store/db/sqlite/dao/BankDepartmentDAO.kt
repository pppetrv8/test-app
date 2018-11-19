package com.example.pppetrv.testapplication.store.db.sqlite.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.pppetrv.testapplication.model.AccountCurrency
import com.example.pppetrv.testapplication.model.BankDepartment
import com.example.pppetrv.testapplication.store.db.sqlite.DataBaseHelper.Companion.TABLE_BANK_DEPS

class BankDepartmentDAO(context: Context): BaseDAO<BankDepartment>(context) {

    companion object {
        // columns of the bank departments table
        private const val COLUMN_BANKS_ID = COLUMN_ID
        private const val COLUMN_BANKS_NAME = "name"
        private const val COLUMN_BANKS_DESCRIPTION = "description"
        private const val COLUMN_BANKS_TIME_WORK = "time_work"
        private const val COLUMN_BANKS_NUMBER = "number"
        private const val COLUMN_BANKS_LONGITUDE = "longitude"
        private const val COLUMN_BANKS_LATITUDE = "latitude"
        private const val COLUMN_BANKS_PREVIOUS_TIME = "previous_time"
        private const val COLUMN_BANKS_NEXT_TIME = "next_time"
        private const val COLUMN_BANKS_FROM_TIME = "from_time"
        private const val COLUMN_BANKS_NATIONAL_CURRENCY = "national_currency"

        val allColumns = arrayOf(
                COLUMN_BANKS_ID,
                COLUMN_BANKS_NAME,
                COLUMN_BANKS_DESCRIPTION,
                COLUMN_BANKS_TIME_WORK,
                COLUMN_BANKS_NUMBER,
                COLUMN_BANKS_LONGITUDE,
                COLUMN_BANKS_LATITUDE,
                COLUMN_BANKS_PREVIOUS_TIME,
                COLUMN_BANKS_NEXT_TIME,
                COLUMN_BANKS_FROM_TIME,
                COLUMN_BANKS_NATIONAL_CURRENCY)

        const val SQL_CREATE_TABLE_BANKS = ("CREATE TABLE $TABLE_BANK_DEPS"
                + "($COLUMN_BANKS_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_BANKS_NAME TEXT, "
                + "$COLUMN_BANKS_DESCRIPTION TEXT, "
                + "$COLUMN_BANKS_TIME_WORK TEXT, "
                + "$COLUMN_BANKS_NUMBER INTEGER NOT NULL, "
                + "$COLUMN_BANKS_LONGITUDE REAL NOT NULL, "
                + "$COLUMN_BANKS_LATITUDE REAL NOT NULL, "
                + "$COLUMN_BANKS_PREVIOUS_TIME TEXT, "
                + "$COLUMN_BANKS_NEXT_TIME TEXT, "
                + "$COLUMN_BANKS_FROM_TIME TEXT, "
                + "$COLUMN_BANKS_NATIONAL_CURRENCY TEXT)")

        const val SQL_DROP_TABLE_BANKS = "DROP TABLE IF EXISTS $TABLE_BANK_DEPS"
    }

    override fun getContentValues(data: BankDepartment): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_BANKS_ID, data.getId())
        values.put(COLUMN_BANKS_NAME, data.name)
        values.put(COLUMN_BANKS_DESCRIPTION, data.description)
        values.put(COLUMN_BANKS_TIME_WORK, data.timeWork)
        values.put(COLUMN_BANKS_NUMBER, data.number)
        values.put(COLUMN_BANKS_LONGITUDE, data.longitude)
        values.put(COLUMN_BANKS_LATITUDE, data.latitude)
        values.put(COLUMN_BANKS_PREVIOUS_TIME, data.previousTime)
        values.put(COLUMN_BANKS_NEXT_TIME, data.nextTime)
        values.put(COLUMN_BANKS_FROM_TIME, data.fromTime)
        values.put(COLUMN_BANKS_NATIONAL_CURRENCY, data.nationalCurrency?.name)
        return values
    }

    override fun cursorToItem(cursor: Cursor): BankDepartment {
        val bank = BankDepartment()
        bank.bankId = cursor.getLong(cursor.getColumnIndex(COLUMN_BANKS_ID))
        bank.name = cursor.getString(cursor.getColumnIndex(COLUMN_BANKS_NAME))
        bank.description = cursor.getString(cursor.getColumnIndex(COLUMN_BANKS_DESCRIPTION))
        bank.timeWork = cursor.getString(cursor.getColumnIndex(COLUMN_BANKS_TIME_WORK))
        bank.number = cursor.getLong(cursor.getColumnIndex(COLUMN_BANKS_NUMBER))
        bank.longitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_BANKS_LONGITUDE))
        bank.latitude = cursor.getDouble(cursor.getColumnIndex(COLUMN_BANKS_LATITUDE))
        bank.previousTime = cursor.getString(cursor.getColumnIndex(COLUMN_BANKS_PREVIOUS_TIME))
        bank.nextTime = cursor.getString(cursor.getColumnIndex(COLUMN_BANKS_NEXT_TIME))
        bank.fromTime = cursor.getString(cursor.getColumnIndex(COLUMN_BANKS_FROM_TIME))
        val natCurrency = cursor.getString(cursor.getColumnIndex(COLUMN_BANKS_NATIONAL_CURRENCY))
        bank.nationalCurrency = AccountCurrency.getValue(natCurrency)
        return bank
    }

    override fun getTableName(): String {
        return TABLE_BANK_DEPS
    }

    override fun getAllColumns(): Array<String> {
        return allColumns
    }
}
