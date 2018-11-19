package com.example.pppetrv.testapplication.net

import com.example.pppetrv.testapplication.net.dto.BankDepartmentDto
import com.example.pppetrv.testapplication.util.Constants.CURRENCY_MAIN_TRADER
import com.example.pppetrv.testapplication.util.Constants.SERVICE_PATH

import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {

    @GET(SERVICE_PATH)
    @Headers("Content-Type:application/json")
    fun getCurrencyRates(@Query("trader") trader: Int = CURRENCY_MAIN_TRADER): Call<BankDepartmentDto>
}