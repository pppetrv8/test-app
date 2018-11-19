package com.example.pppetrv.testapplication.store.db.realm.dbo

import com.google.gson.annotations.SerializedName
import io.realm.RealmList

import java.io.Serializable

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmBankDepartment : RealmObject(), Serializable {

    @PrimaryKey
    @SerializedName("bankId")
    var bankId = 0L
    @SerializedName("name")
    var name: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("timeWork")
    var timeWork: String? = null
    @SerializedName("number")
    var number = 0L
    @SerializedName("longitude")
    var longitude = 0.0
    @SerializedName("latitude")
    var latitude = 0.0
    @SerializedName("previousTime")
    var previousTime: String? = null
    @SerializedName("nextTime")
    var nextTime: String? = null
    @SerializedName("fromTime")
    var fromTime: String? = null
    @SerializedName("nationalCurrency")
    var nationalCurrency: String? = null
    //@SerializedName("currencyRates")
    //var currencyRates: RealmList<RealmCurrencyRate>? = null


}
