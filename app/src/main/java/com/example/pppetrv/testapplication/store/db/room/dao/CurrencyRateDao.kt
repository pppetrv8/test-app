package com.example.pppetrv.testapplication.store.db.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.pppetrv.testapplication.store.db.room.entity.CurrencyRateEntity
import io.reactivex.Flowable

@Dao
interface CurrencyRateDao {

    @Query("select * from cur_rates WHERE bank_id = :bankId")
    abstract fun getCurrencyRates(bankId: Int): Flowable<List<CurrencyRateEntity>>

    @Query("select * from cur_rates WHERE rate_id = :rateId")
    abstract fun getCurrencyRate(rateId: Long): Flowable<CurrencyRateEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencyRate(rate: CurrencyRateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencyRates(rates: List<CurrencyRateEntity>)
}