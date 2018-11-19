package com.example.pppetrv.testapplication.store.db.sqlite.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import com.example.pppetrv.testapplication.model.Item

import com.example.pppetrv.testapplication.store.db.sqlite.DataBaseHelper
import timber.log.Timber

abstract class BaseDAO<T: Item>(val context: Context) {

    private var database: SQLiteDatabase? = null
        get() = field
    private val dbHelper: DataBaseHelper?


    companion object {
        const val COLUMN_ID = "_id"
        const val REAL_NOT_NULL = "REAL NOT NULL"
    }

    init {
        dbHelper = DataBaseHelper.getInstance()
        try {
            open()
        } catch (e: SQLException) {
            Timber.e(e)
        }
    }

    protected abstract fun getContentValues(data: T): ContentValues
    protected abstract fun cursorToItem(cursor: Cursor): T
    protected abstract fun getTableName(): String
    protected abstract fun getAllColumns(): Array<String>

    fun putItem(data: T): Long {
        var insertId: Long = -1
        val db = database
        try {
            if (db != null) {
                db.beginTransaction()
                val values = getContentValues(data)
                // we do replace instead of insert command that allows to replace
                // with new values if item already exists in db
                insertId = db.replace(getTableName(), null, values)
                db.setTransactionSuccessful()
            }
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            db?.endTransaction()
        }
        return insertId
    }

    fun putItems(items: List<T>): Int {
        for (item in items) {
            putItem(item)
        }
        return 0
    }

    fun deleteItem(value: T): Int {
        val id = value.getId()
        return deleteItem(id)
    }

    fun deleteItem(id: Long): Int {
        var count = 0
        val db = database
        try {
            if (db != null) {
                db.beginTransaction()
                count = db.delete(getTableName(), "$COLUMN_ID=$id", null)
                deleteFromSequence(getTableName())
                db.setTransactionSuccessful()
            }
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            db?.endTransaction()
        }
        return count
    }

    fun getItem(id: Long): T? {
        try {
            val cursor = database?.query(getTableName(), getAllColumns(), "$COLUMN_ID=?",
                    arrayOf(id.toString()), null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                return cursorToItem(cursor)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return null
    }

    fun getItems(): List<T> {
        return getItems(null)
    }

    fun getItems(selection: String?): List<T> {
        val itemsList = ArrayList<T>()
        try {
            val cursor = database?.query(getTableName(), getAllColumns(), selection,
                null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val item = cursorToItem(cursor)
                    itemsList.add(item)
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return itemsList
    }

    protected fun getAllItemsCount(table: String): Long {
        return DatabaseUtils.queryNumEntries(database, table)
    }

    protected fun getAllItemsCountForId(table: String, selection: String): Long {
        return DatabaseUtils.queryNumEntries(database, table, selection)
    }

    fun getAvg(column: String, table: String, queryStr: String): Double {
        var avg = 0.0
        try {
            val calcDistanceQuery = ("SELECT AVG($column) FROM $table WHERE $queryStr")
            val cursor = database?.rawQuery(calcDistanceQuery, null)
            if (cursor != null) {
                cursor.moveToFirst()
                avg = cursor.getDouble(0)
                cursor.close()
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return avg
    }

    fun getSum(column: String, table: String, queryStr: String): Double {
        var sum = 0.0
        val whereStr = if (!TextUtils.isEmpty(queryStr)) " WHERE $queryStr" else ""
        try {
            val calcDistanceQuery = "SELECT SUM($column) FROM $table$whereStr"
            val cursor = database?.rawQuery(calcDistanceQuery, null)
            if (cursor != null) {
                cursor.moveToFirst()
                sum = cursor.getDouble(0)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return sum
    }

    fun deleteFromSequence(tableName: String) {
        database?.execSQL(DataBaseHelper.DELETE_FROM_SQLITE_SEQUENCE + tableName + "'")
    }

    fun open() {
        try {
            database = dbHelper?.writableDatabase
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    fun close() {
        dbHelper?.close()
    }
}
