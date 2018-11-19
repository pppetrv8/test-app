package com.example.pppetrv.testapplication.store.repository

import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.model.BankDepartment
import io.reactivex.Flowable

interface Repository {

    fun getCurrencyRates(bankId: Int, useCache: Boolean) : Flowable<List<CurrencyRate>>

    fun getBankDepartment(bankId: Int, useCache: Boolean) : Flowable<BankDepartment>

    fun storeCurrencyRates(bankId: Int, list: List<CurrencyRate>): Flowable<List<CurrencyRate>>

    fun storeBankDepartment(result: BankDepartment): Flowable<BankDepartment>
}