package com.example.pppetrv.testapplication.store.db.room.entity

import android.arch.persistence.room.*
import android.support.annotation.NonNull
import com.example.pppetrv.testapplication.model.AccountCurrency
import com.example.pppetrv.testapplication.model.BankDepartment

@Entity(tableName = "bank_department")
data class BankDepartmentEntity(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "bank_id")
    var bankId: Long = 0L,
    @ColumnInfo(name = "bank_name")
    var name: String? = null,
    @ColumnInfo(name = "bank_description")
    var description: String? = null,
    @ColumnInfo(name = "bank_time_work")
    var timeWork: String? = null,
    @ColumnInfo(name = "bank_number")
    var number: Long = 0L,
    @ColumnInfo(name = "bank_longitude")
    var longitude: Double = 0.0,
    @ColumnInfo(name = "bank_latitude")
    var latitude: Double = 0.0,
    @ColumnInfo(name = "bank_previous_time")
    var previousTime: String? = null,
    @ColumnInfo(name = "bank_next_time")
    var nextTime: String? = null,
    @ColumnInfo(name = "bank_from_time")
    var fromTime: String? = null,
    @ColumnInfo(name = "bank_national_currency")
    var nationalCurrency: AccountCurrency? = null
    //@Embedded(prefix = "bank_currency_rates")
    //var currencyRates: List<CurrencyRateEntity>? = null
) {

    @Ignore
    fun toBankDepartment(): BankDepartment {
        var bank = BankDepartment()
        bank.bankId = bankId
        bank.name = name
        bank.description = description
        bank.timeWork = timeWork
        bank.number = number
        bank.longitude = longitude
        bank.latitude = latitude
        bank.previousTime = previousTime
        bank.nextTime = nextTime
        bank.fromTime = fromTime
        bank.nationalCurrency = nationalCurrency
        //bank.currencyRates = CurrencyRateEntity.toCurrencyRateList(currencyRates)
        return bank
    }

    @Ignore
    fun fromBankDepartment(bank: BankDepartment) {
        bankId = bank.bankId
        name = bank.name
        description = bank.description
        timeWork = bank.timeWork
        number = bank.number
        longitude = bank.longitude
        latitude = bank.latitude
        previousTime = bank.previousTime
        nextTime = bank.nextTime
        fromTime = bank.fromTime
        nationalCurrency = bank.nationalCurrency
        //bankEntity.currencyRates = CurrencyRateEntity.toCurrencyRateEntityList(bank.currencyRates)
    }
}
