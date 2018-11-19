package com.example.pppetrv.testapplication.store.repository

import com.example.pppetrv.testapplication.model.BankDepartment
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.net.DtoConverter
import com.example.pppetrv.testapplication.net.NetService
import com.example.pppetrv.testapplication.util.rx.SchedulerProvider
import com.example.pppetrv.testapplication.store.db.DbProvider
import io.reactivex.Flowable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private var dbProvider: DbProvider,
    private var netService: NetService,
    private var schedulerProvider: SchedulerProvider): Repository {

    override fun getCurrencyRates(bankId: Int, useCache: Boolean) : Flowable<List<CurrencyRate>> {
        return if (useCache) {
            dbProvider.getCurrencyRates(bankId) as Flowable<List<CurrencyRate>>
        } else {
            getCurrencyRatesNet(bankId)
        }
    }

    private fun getCurrencyRatesNet(bankId: Int) : Flowable<List<CurrencyRate>> {
        return netService.getCurrencyRates(bankId).
        subscribeOn(schedulerProvider.io()).
        concatMap { dto ->
            val converter = DtoConverter.getConverter()
            val result = converter?.toBankDepartment(dto)
            val rates = result?.currencyRates
            storeCurrencyRates(bankId, rates as List<CurrencyRate>)
        }
    }

    override fun getBankDepartment(bankId: Int, useCache: Boolean) : Flowable<BankDepartment> {
        return if (useCache) {
            dbProvider.getBankDepartment(bankId) as Flowable<BankDepartment>
        } else {
            getBankDepartmentNet(bankId)
        }
    }

    private fun getBankDepartmentNet(bankId: Int) : Flowable<BankDepartment> {
         return netService.getCurrencyRates(bankId).
         subscribeOn(schedulerProvider.io()).
         concatMap { dto ->
             val converter = DtoConverter.getConverter()
             val bank = converter?.toBankDepartment(dto)
             storeBankDepartment(bank as BankDepartment)
         }
    }

    override fun storeCurrencyRates(bankId: Int, list: List<CurrencyRate>): Flowable<List<CurrencyRate>> {
        return Flowable.fromCallable { dbProvider.storeCurrencyRates(bankId, list) }.
            subscribeOn(schedulerProvider.io())
    }

    override fun storeBankDepartment(result: BankDepartment): Flowable<BankDepartment> {
        return Flowable.fromCallable { dbProvider.storeBankDepartment(result) }.
            subscribeOn(schedulerProvider.io())
    }
}