package com.example.pppetrv.testapplication.model

class BankDepartment: Item {

    var bankId = 0L
    var name: String? = null
    var description: String? = null
    var timeWork: String? = null
    var number = 0L
    var longitude = 0.0
    var latitude = 0.0
    var previousTime: String? = null
    var nextTime: String? = null
    var fromTime: String? = null
    var nationalCurrency: AccountCurrency? = null
    var currencyRates: List<CurrencyRate>? = null

    override fun getId(): Long {
        return bankId
    }

    fun findCurrencyRate(rate: CurrencyRate?): CurrencyRate? {
        var ratesObj: CurrencyRate? = null
        if (currencyRates != null && rate != null) {
            val idx = currencyRates?.indexOf(rate) as Int
            if (idx >= 0 && idx < currencyRates?.size as Int) {
                ratesObj = currencyRates?.get(idx)
            }
        }
        return ratesObj
    }

    fun getCurrencyRate(position: Int): CurrencyRate? {
        if (currencyRates != null && position < currencyRates?.size as Int) {
            return currencyRates?.get(position)
        }
        return null
    }
}