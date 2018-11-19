package com.example.pppetrv.testapplication.model

import timber.log.Timber

class CurrencyRate : Cloneable, Item {

    var bankId = 0L
    var buyGoodId = 0
    var sellGoodId = 0
    var buyAmount = 0.0
    var sellAmount = 0.0
    var sellAmountDelta = 0.0
    var buyAmountDelta = 0.0
    var buyGoodName: AccountCurrency? = null
    var sellGoodName: AccountCurrency? = null
    var buyGoodTitle: String? = null
    var sellGoodTitle: String? = null
    var itemOrder = 0
    var buyGoodScale = 0.0
    var selected: Boolean = false

    override fun getId(): Long {
        return bankId xor buyGoodId.toLong() xor sellGoodId.toLong()
    }

    override fun hashCode(): Int {
        var code = super.hashCode()
        if (buyGoodName != null) {
            code = code xor buyGoodName?.hashCode() as Int
        }
        if (sellGoodName != null) {
            code = code xor sellGoodName?.hashCode() as Int
        }
        return code
    }

    override fun equals(obj: Any?): Boolean {
        if (obj == null) {
            return false
        }
        if (obj !is CurrencyRate) {
            return false
        }
        val rate = obj as CurrencyRate?
        var result = true
        if (buyGoodName != null) {
            result = result and (buyGoodName == rate?.buyGoodName)
        }
        if (sellGoodName != null) {
            result = result and (sellGoodName == rate?.sellGoodName)
        }
        return result
    }

    override fun clone(): CurrencyRate {
        try {
            return super.clone() as CurrencyRate
        } catch (e: Exception) {
            Timber.e(e)
        }
        return CurrencyRate()
    }
}