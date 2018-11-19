package com.example.pppetrv.testapplication.store.db.sqlite.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.pppetrv.testapplication.model.AccountCurrency
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.store.db.sqlite.DataBaseHelper
import com.example.pppetrv.testapplication.store.db.sqlite.DataBaseHelper.Companion.TABLE_CUR_RATES

class CurrencyRatesDAO(context: Context): BaseDAO<CurrencyRate>(context) {

    companion object {

        // columns of the currency rates table
        private const val COLUMN_RATES_ID = COLUMN_ID
        private const val COLUMN_RATES_BANK_ID = "bank_id"
        private const val COLUMN_RATES_BUY_GOOD_ID = "buy_good_id"
        private const val COLUMN_RATES_SELL_GOOD_ID = "sell_good_id"
        private const val COLUMN_RATES_SELL_AMOUNT = "sell_amount"
        private const val COLUMN_RATES_BUY_AMOUNT = "buy_amount"
        private const val COLUMN_RATES_SELL_AMOUNT_DELTA = "sell_amount_delta"
        private const val COLUMN_RATES_BUY_AMOUNT_DELTA = "buy_amount_delta"
        private const val COLUMN_RATES_BUY_GOOD_NAME = "buy_good_name"
        private const val COLUMN_RATES_SELL_GOOD_NAME = "sell_good_name"
        private const val COLUMN_RATES_BUY_GOOD_TITLE = "buy_good_title"
        private const val COLUMN_RATES_SELL_GOOD_TITLE = "sell_good_title"
        private const val COLUMN_RATES_ITEM_ORDER = "item_order"
        private const val COLUMN_RATES_BUY_GOOD_SCALE = "buy_good_scale"

        val allColumns = arrayOf(
            COLUMN_RATES_ID,
            COLUMN_RATES_BANK_ID,
            COLUMN_RATES_BUY_GOOD_ID,
            COLUMN_RATES_SELL_GOOD_ID,
            COLUMN_RATES_SELL_AMOUNT,
            COLUMN_RATES_SELL_AMOUNT_DELTA,
            COLUMN_RATES_BUY_AMOUNT,
            COLUMN_RATES_BUY_AMOUNT_DELTA,
            COLUMN_RATES_BUY_GOOD_NAME,
            COLUMN_RATES_SELL_GOOD_NAME,
            COLUMN_RATES_BUY_GOOD_TITLE,
            COLUMN_RATES_SELL_GOOD_TITLE,
            COLUMN_RATES_ITEM_ORDER,
            COLUMN_RATES_BUY_GOOD_SCALE)

        const val SQL_CREATE_TABLE_CUR_RATES = ("CREATE TABLE ${DataBaseHelper.TABLE_CUR_RATES}"
                + "($COLUMN_RATES_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_RATES_BANK_ID INTEGER NOT NULL, "
                + "$COLUMN_RATES_BUY_GOOD_ID INTEGER NOT NULL, "
                + "$COLUMN_RATES_SELL_GOOD_ID INTEGER NOT NULL, "
                + "$COLUMN_RATES_SELL_AMOUNT REAL NOT NULL, "
                + "$COLUMN_RATES_SELL_AMOUNT_DELTA REAL NOT NULL, "
                + "$COLUMN_RATES_BUY_AMOUNT REAL NOT NULL, "
                + "$COLUMN_RATES_BUY_AMOUNT_DELTA REAL NOT NULL, "
                + "$COLUMN_RATES_BUY_GOOD_NAME TEXT, "
                + "$COLUMN_RATES_SELL_GOOD_NAME TEXT, "
                + "$COLUMN_RATES_BUY_GOOD_TITLE TEXT, "
                + "$COLUMN_RATES_SELL_GOOD_TITLE TEXT, "
                + "$COLUMN_RATES_ITEM_ORDER INTEGER NOT NULL, "
                + "$COLUMN_RATES_BUY_GOOD_SCALE REAL NOT NULL)")

        const val SQL_DROP_TABLE_CUR_RATES = "DROP TABLE IF EXISTS ${DataBaseHelper.TABLE_CUR_RATES}"
    }

    override fun getContentValues(data: CurrencyRate): ContentValues {
        val values = ContentValues()
        values.put(COLUMN_RATES_ID, data.getId())
        values.put(COLUMN_RATES_BANK_ID, data.bankId)
        values.put(COLUMN_RATES_BUY_GOOD_ID, data.buyGoodId)
        values.put(COLUMN_RATES_SELL_GOOD_ID, data.sellGoodId)
        values.put(COLUMN_RATES_SELL_AMOUNT, data.sellAmount)
        values.put(COLUMN_RATES_SELL_AMOUNT_DELTA, data.sellAmountDelta)
        values.put(COLUMN_RATES_BUY_AMOUNT, data.buyAmount)
        values.put(COLUMN_RATES_BUY_AMOUNT_DELTA, data.buyAmountDelta)
        values.put(COLUMN_RATES_BUY_GOOD_NAME, data.buyGoodName?.name)
        values.put(COLUMN_RATES_SELL_GOOD_NAME, data.sellGoodName?.name)
        values.put(COLUMN_RATES_BUY_GOOD_TITLE, data.buyGoodTitle)
        values.put(COLUMN_RATES_SELL_GOOD_TITLE, data.sellGoodTitle)
        values.put(COLUMN_RATES_ITEM_ORDER, data.itemOrder)
        values.put(COLUMN_RATES_BUY_GOOD_SCALE, data.buyGoodScale)
        return values
    }

    override fun cursorToItem(cursor: Cursor): CurrencyRate {
        val curRate = CurrencyRate()
        curRate.bankId = cursor.getLong(cursor.getColumnIndex(COLUMN_RATES_BANK_ID))
        curRate.buyGoodId = cursor.getInt(cursor.getColumnIndex(COLUMN_RATES_BUY_GOOD_ID))
        curRate.sellGoodId = cursor.getInt(cursor.getColumnIndex(COLUMN_RATES_SELL_GOOD_ID))
        curRate.sellAmount = cursor.getDouble(cursor.getColumnIndex(COLUMN_RATES_SELL_AMOUNT))
        curRate.sellAmountDelta = cursor.getDouble(cursor.getColumnIndex(COLUMN_RATES_SELL_AMOUNT_DELTA))
        curRate.buyAmount = cursor.getDouble(cursor.getColumnIndex(COLUMN_RATES_BUY_AMOUNT))
        curRate.buyAmountDelta = cursor.getDouble(cursor.getColumnIndex(COLUMN_RATES_BUY_AMOUNT_DELTA))
        val buyGoodNameStr = cursor.getString(cursor.getColumnIndex(COLUMN_RATES_BUY_GOOD_NAME))
        val sellGoodNameStr = cursor.getString(cursor.getColumnIndex(COLUMN_RATES_SELL_GOOD_NAME))
        curRate.buyGoodName = AccountCurrency.getValue(buyGoodNameStr)
        curRate.sellGoodName = AccountCurrency.getValue(sellGoodNameStr)
        curRate.buyGoodTitle = cursor.getString(cursor.getColumnIndex(COLUMN_RATES_BUY_GOOD_TITLE))
        curRate.sellGoodTitle = cursor.getString(cursor.getColumnIndex(COLUMN_RATES_SELL_GOOD_TITLE))
        curRate.itemOrder = cursor.getInt(cursor.getColumnIndex(COLUMN_RATES_ITEM_ORDER))
        curRate.buyGoodScale = cursor.getDouble(cursor.getColumnIndex(COLUMN_RATES_BUY_GOOD_SCALE))
        return curRate
    }

    fun getBankItems(bankId: Int): List<CurrencyRate> {
        val selection = "$COLUMN_RATES_BANK_ID = $bankId"
        return getItems(selection)
    }

    fun putBankItems(bankId: Int, items: List<CurrencyRate>): Int {
        for (item in items) {
            item.bankId = bankId.toLong()
            putItem(item)
        }
        return 0
    }

    override fun getTableName(): String {
        return TABLE_CUR_RATES
    }

    override fun getAllColumns(): Array<String> {
        return allColumns
    }
}
