package com.example.pppetrv.testapplication.store.db.realm.dbo

import com.example.pppetrv.testapplication.model.AccountCurrency
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.google.gson.annotations.SerializedName

import java.io.Serializable

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmCurrencyRate : RealmObject(), Serializable {

    @PrimaryKey
    @SerializedName("id")
    var id: Long = 0
    @SerializedName("bankId")
    var bankId = 0L
    @SerializedName("buyGoodId")
    var buyGoodId = 0
    @SerializedName("sellGoodId")
    var sellGoodId = 0
    @SerializedName("buyAmount")
    var buyAmount = 0.0
    @SerializedName("sellAmount")
    var sellAmount = 0.0
    @SerializedName("sellAmountDelta")
    var sellAmountDelta = 0.0
    @SerializedName("buyAmountDelta")
    var buyAmountDelta = 0.0
    @SerializedName("buyGoodName")
    var buyGoodName: String? = null
    @SerializedName("sellGoodName")
    var sellGoodName: String? = null
    @SerializedName("buyGoodTitle")
    var buyGoodTitle: String? = null
    @SerializedName("sellGoodTitle")
    var sellGoodTitle: String? = null
    @SerializedName("itemOrder")
    var itemOrder = 0
    @SerializedName("buyGoodScale")
    var buyGoodScale = 0.0

    fun convertToCurrencyRate(bankId: Long): CurrencyRate {
        val rate = CurrencyRate()
        rate.bankId = bankId
        rate.buyGoodId = buyGoodId
        rate.sellGoodId = sellGoodId
        rate.buyAmount = buyAmount
        rate.sellAmount = sellAmount
        rate.sellAmountDelta = sellAmountDelta
        rate.buyAmountDelta = buyAmountDelta
        rate.buyGoodName = AccountCurrency.getValue(buyGoodName)
        rate.sellGoodName = AccountCurrency.getValue(sellGoodName)
        rate.buyGoodTitle = buyGoodTitle
        rate.sellGoodTitle = sellGoodTitle
        rate.itemOrder = itemOrder
        rate.buyGoodScale = buyGoodScale
        return rate
    }

    fun convertFromCurrencyRate(curBankId: Long, item: CurrencyRate) {
        item.bankId = curBankId
        id = item.getId()
        bankId = curBankId
        buyGoodId = item.buyGoodId
        sellGoodId = item.sellGoodId
        buyAmount = item.buyAmount
        sellAmount = item.sellAmount
        sellAmountDelta = item.sellAmountDelta
        buyAmountDelta = item.buyAmountDelta
        buyGoodName = item.buyGoodName?.name
        sellGoodName = item.sellGoodName?.name
        buyGoodTitle = item.buyGoodTitle
        sellGoodTitle = item.sellGoodTitle
        itemOrder = item.itemOrder
        buyGoodScale = item.buyGoodScale
    }
}
