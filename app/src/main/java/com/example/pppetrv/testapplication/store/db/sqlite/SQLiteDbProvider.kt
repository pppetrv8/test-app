package com.example.pppetrv.testapplication.store.db.sqlite

import android.content.Context
import com.example.pppetrv.testapplication.App
import com.example.pppetrv.testapplication.model.BankDepartment
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.store.db.DbProvider
import com.example.pppetrv.testapplication.store.db.sqlite.dao.BankDepartmentDAO
import com.example.pppetrv.testapplication.store.db.sqlite.dao.CurrencyRatesDAO
import io.reactivex.Flowable
import javax.inject.Inject

class SQLiteDbProvider @Inject constructor() : DbProvider() {

    private val bankDepartmentDAO: BankDepartmentDAO?
    private val currencyRatesDAO: CurrencyRatesDAO?

    override fun getCurrencyRate(rateId: Long): Flowable<CurrencyRate> {
        return Flowable.fromCallable { currencyRatesDAO?.getItem(rateId) }
    }

    override fun getBankDepartment(bankId: Int): Flowable<BankDepartment> {
        return Flowable.fromCallable { bankDepartmentDAO?.getItem(bankId.toLong()) }
    }

    override fun getBankDepartments(): Flowable<List<BankDepartment>> {
        return Flowable.fromCallable { bankDepartmentDAO?.getItems() }
    }

    override fun getCurrencyRates(bankId: Int): Flowable<List<CurrencyRate>> {
        return Flowable.fromCallable { currencyRatesDAO?.getBankItems(bankId) }
    }

    override fun storeBankDepartment(bank: BankDepartment): BankDepartment {
        bankDepartmentDAO?.putItem(bank)
        return bank
    }

    override fun storeCurrencyRates(bankId: Int, rates: List<CurrencyRate>): List<CurrencyRate> {
        currencyRatesDAO?.putBankItems(bankId, rates)
        return rates
    }

    init {
        bankDepartmentDAO = BankDepartmentDAO(App.appInstance as Context)
        currencyRatesDAO = CurrencyRatesDAO(App.appInstance as Context)
    }
}
