package com.example.pppetrv.testapplication.store.db.realm

import com.example.pppetrv.testapplication.model.BankDepartment
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.store.db.DbProvider
import com.example.pppetrv.testapplication.store.db.realm.dbo.RealmCurrencyRate
import io.reactivex.Flowable
import io.realm.*
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject

class RealmDbProvider @Inject constructor() : DbProvider() {

    companion object {
        const val REALM_SCHEMA_VERSION: Long = 1
    }

    override fun getCurrencyRate(rateId: Long): Flowable<CurrencyRate> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCurrencyRates(bankId: Int): Flowable<List<CurrencyRate>> {
        return Flowable.fromCallable { findCurrencyRates(bankId.toLong()) }
    }

    override fun getBankDepartment(bankId: Int): Flowable<BankDepartment> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getBankDepartments(): Flowable<List<BankDepartment>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun storeCurrencyRates(bankId: Int, rates: List<CurrencyRate>): List<CurrencyRate> {
        saveCurrencyRateList(bankId, rates)
        return rates
    }

    override fun storeBankDepartment(bank: BankDepartment): BankDepartment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun findCurrencyRates(bankId: Long): List<CurrencyRate> {
        val result = ArrayList<CurrencyRate>()
        var realm: Realm? = null
        try {
            val config = getRealmConfig()
            realm = Realm.getInstance(config)
            val query = realm?.where(RealmCurrencyRate::class.java)?.equalTo("bankId", bankId)
            val realmResults = query?.findAll()
            for (i in realmResults as RealmResults) {
                val item = i.convertToCurrencyRate(bankId)
                result.add(item)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        realm?.close()
        return result
    }

    private fun saveCurrencyRateList(bankId: Int, items: List<CurrencyRate>): Int {
        var realm: Realm? = null
        var count = 0
        try {
            val config = getRealmConfig()
            realm = Realm.getInstance(config)
            val realmItems = RealmList<RealmCurrencyRate>()
            realm?.beginTransaction()
            for (i in items) {
                val item = RealmCurrencyRate()
                item.convertFromCurrencyRate(bankId.toLong(), i)
                realmItems.add(item)
                count++
            }
            realm.copyToRealmOrUpdate(realmItems)
            realm.commitTransaction()
        } catch (e: Exception) {
            Timber.e(e)
        }
        realm?.close()
        return count
    }

    fun updateCurrencyRate(bankId: Int, item: CurrencyRate): Boolean {
        var realm: Realm? = null
        var succeed = false
        try {
            val config = getRealmConfig()
            realm = Realm.getInstance(config)
            realm?.beginTransaction()
            val realmItem = RealmCurrencyRate()
            realmItem.convertFromCurrencyRate(bankId.toLong(), item)
            realm.insertOrUpdate(realmItem)
            realm.commitTransaction()
            succeed = true
        } catch (e: Exception) {
            Timber.e(e)
        }
        realm?.close()
        return succeed
    }

    private fun getRealmConfig(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .schemaVersion(REALM_SCHEMA_VERSION)
            .deleteRealmIfMigrationNeeded()
            .build()
    }

    private fun clearDbTable(clazz: Class<RealmModel>) {
        var realm: Realm? = null
        try {
            val config = getRealmConfig()
            realm = Realm.getInstance(config)
            realm?.beginTransaction()
            realm.delete(clazz)
            realm.commitTransaction()
        } catch (e: Exception) {
            Timber.e(e)
        }
        realm?.close()
    }
}
