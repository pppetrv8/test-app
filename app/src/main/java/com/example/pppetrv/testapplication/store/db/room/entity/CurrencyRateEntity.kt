package com.example.pppetrv.testapplication.store.db.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import com.example.pppetrv.testapplication.model.AccountCurrency
import com.example.pppetrv.testapplication.model.CurrencyRate

@Entity(tableName = "cur_rates")
data class CurrencyRateEntity(

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "rate_id")
    var id: Long = 0L,
    @ColumnInfo(name = "bank_id")
    var bankId: Long = 0L,
    @ColumnInfo(name = "buy_good_id")
    var buyGoodId: Int = 0,
    @ColumnInfo(name = "sell_good_id")
    var sellGoodId: Int = 0,
    @ColumnInfo(name = "buy_amount")
    var buyAmount: Double = 0.0,
    @ColumnInfo(name = "sell_amount")
    var sellAmount: Double = 0.0,
    @ColumnInfo(name = "sell_amount_delta")
    var sellAmountDelta: Double = 0.0,
    @ColumnInfo(name = "buy_amount_delta")
    var buyAmountDelta: Double = 0.0,
    @ColumnInfo(name = "buy_good_name")
    var buyGoodName: AccountCurrency? = null,
    @ColumnInfo(name = "sell_good_name")
    var sellGoodName: AccountCurrency? = null,
    @ColumnInfo(name = "buy_good_title")
    var buyGoodTitle: String? = null,
    @ColumnInfo(name = "sell_good_title")
    var sellGoodTitle: String? = null,
    @ColumnInfo(name = "item_order")
    var itemOrder: Int = 0,
    @ColumnInfo(name = "buy_good_scale")
    var buyGoodScale: Double = 0.0

) {
    @Ignore
    fun toCurrencyRate(): CurrencyRate {
        var curRate = CurrencyRate()
        curRate.bankId = bankId
        curRate.buyGoodId = buyGoodId
        curRate.sellGoodId = sellGoodId
        curRate.buyAmount = buyAmount
        curRate.sellAmount = sellAmount
        curRate.sellAmountDelta = sellAmountDelta
        curRate.buyAmountDelta = buyAmountDelta
        curRate.buyGoodName = buyGoodName
        curRate.sellGoodName = sellGoodName
        curRate.buyGoodTitle = buyGoodTitle
        curRate.sellGoodTitle = sellGoodTitle
        curRate.itemOrder = itemOrder
        curRate.buyGoodScale = buyGoodScale
        return curRate
    }

    @Ignore
    fun fromCurrencyRate(curBankId: Long, rate: CurrencyRate) {
        rate.bankId = curBankId
        id = rate.getId()
        bankId = curBankId
        buyGoodId = rate.buyGoodId
        sellGoodId = rate.sellGoodId
        buyAmount = rate.buyAmount
        sellAmount = rate.sellAmount
        sellAmountDelta = rate.sellAmountDelta
        buyAmountDelta = rate.buyAmountDelta
        buyGoodName = rate.buyGoodName
        sellGoodName = rate.sellGoodName
        buyGoodTitle = rate.buyGoodTitle
        sellGoodTitle = rate.sellGoodTitle
        itemOrder = rate.itemOrder
        buyGoodScale = rate.buyGoodScale
    }
}
