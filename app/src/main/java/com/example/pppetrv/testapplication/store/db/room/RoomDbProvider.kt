package com.example.pppetrv.testapplication.store.db.room

import com.example.pppetrv.testapplication.model.BankDepartment
import com.example.pppetrv.testapplication.model.CurrencyRate
import android.arch.persistence.room.Room
import android.content.Context
import com.example.pppetrv.testapplication.App
import com.example.pppetrv.testapplication.store.db.DbProvider
import com.example.pppetrv.testapplication.store.db.room.entity.BankDepartmentEntity
import com.example.pppetrv.testapplication.store.db.room.entity.CurrencyRateEntity
import com.example.pppetrv.testapplication.util.Constants.ROOM_DATABASE_NAME
import io.reactivex.Flowable
import javax.inject.Inject

class RoomDbProvider @Inject constructor() : DbProvider() {

    private var appDB: AppDatabase? = null

    init {
        appDB = Room.databaseBuilder(App.appInstance as Context,
                AppDatabase::class.java, ROOM_DATABASE_NAME).build()
    }

    override fun getCurrencyRate(rateId: Long): Flowable<CurrencyRate>? {
        return if (appDB != null && appDB?.currencyRateDao != null) {
            val rateEntityFlowable = appDB?.currencyRateDao?.getCurrencyRate(rateId)
            rateEntityFlowable?.map { item -> item.toCurrencyRate() }
        } else {
            Flowable.fromCallable { CurrencyRate() }
        }
    }

    override fun getCurrencyRates(bankId: Int): Flowable<List<CurrencyRate>>? {
        var rateEntityListFlowableResult: Flowable<List<CurrencyRate>>? = null
        if (appDB != null && appDB?.currencyRateDao != null) {
            val rateEntityListFlowable = appDB?.currencyRateDao?.getCurrencyRates(bankId)
            rateEntityListFlowableResult = rateEntityListFlowable?.map { list ->
                val result = ArrayList<CurrencyRate>()
                for (i in list) {
                    val rate = i.toCurrencyRate()
                    result.add(rate)
                }
                result
            }
        }
        return rateEntityListFlowableResult
    }

    override fun getBankDepartment(bankId: Int): Flowable<BankDepartment>? {
        return if (appDB != null && appDB?.bankDepartmentDao != null) {
            val bankEntityFlowable = appDB?.bankDepartmentDao?.getBankDepartment(bankId)
            bankEntityFlowable?.map { item -> item.toBankDepartment() }
        } else {
            Flowable.fromCallable { BankDepartment() }
        }
    }

    override fun getBankDepartments(): Flowable<List<BankDepartment>>? {
        var bankEntityListFlowableResult: Flowable<List<BankDepartment>>? = null
        if (appDB != null && appDB?.bankDepartmentDao != null) {
            val bankEntityListFlowable = appDB?.bankDepartmentDao?.getBankDepartments()
            bankEntityListFlowableResult = bankEntityListFlowable?.map { list ->
                val result = ArrayList<BankDepartment>()
                for (i in list) {
                    val bank = i.toBankDepartment()
                    result.add(bank)
                }
                result
            }
        }
        return bankEntityListFlowableResult
    }

    override fun storeCurrencyRates(bankId: Int, rates: List<CurrencyRate>): List<CurrencyRate> {
        val entityList = ArrayList<CurrencyRateEntity>()
        if (appDB != null && appDB?.currencyRateDao != null) {
            for (i in rates) {
                val rate = CurrencyRateEntity()
                rate.fromCurrencyRate(bankId.toLong(), i)
                entityList.add(rate)
            }
            appDB?.currencyRateDao?.insertCurrencyRates(entityList)
        }
        return rates
    }

    override fun storeBankDepartment(bank: BankDepartment): BankDepartment {
        if (appDB != null && appDB?.bankDepartmentDao != null) {
            val bankEntity = BankDepartmentEntity()
            bankEntity.fromBankDepartment(bank)
            appDB?.bankDepartmentDao?.insertBankDepartment(bankEntity)
        }
        return bank
    }
}
