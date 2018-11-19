package com.example.pppetrv.testapplication.store.db

import com.example.pppetrv.testapplication.model.BankDepartment
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.store.db.realm.RealmDbProvider
import com.example.pppetrv.testapplication.store.db.room.RoomDbProvider
import com.example.pppetrv.testapplication.store.db.sqlite.SQLiteDbProvider
import io.reactivex.Flowable

abstract class DbProvider {

    companion object {

        fun getDbProvider(type: DBType): DbProvider {
            return when(type) {
                DBType.SQLITE -> {
                    SQLiteDbProvider()
                }
                DBType.ROOM -> {
                    RoomDbProvider()
                }
                DBType.REALM -> {
                    RealmDbProvider()
                }
            }
        }
    }

    abstract fun getCurrencyRate(rateId: Long): Flowable<CurrencyRate>?

    abstract fun getCurrencyRates(bankId: Int): Flowable<List<CurrencyRate>>?

    abstract fun getBankDepartment(bankId: Int): Flowable<BankDepartment>?

    abstract fun getBankDepartments(): Flowable<List<BankDepartment>>?

    abstract fun storeCurrencyRates(bankId: Int, rates: List<CurrencyRate>): List<CurrencyRate>

    abstract fun storeBankDepartment(bank: BankDepartment): BankDepartment
}
