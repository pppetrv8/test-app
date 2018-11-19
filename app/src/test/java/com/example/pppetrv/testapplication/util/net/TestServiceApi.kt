package com.example.pppetrv.testapplication.util.net

import com.example.pppetrv.testapplication.net.ServiceApi
import com.example.pppetrv.testapplication.net.dto.BankDepartmentDto
import com.example.pppetrv.testapplication.util.TestUtils
import retrofit2.Call
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate

class TestServiceApi(private var delegate: BehaviorDelegate<ServiceApi>): ServiceApi {

    override fun getCurrencyRates(trader: Int, service: Int, lang: String): Call<BankDepartmentDto> {
        val response = Response.success(200, getBankDepartmentDto())
        return delegate.returningResponse(response).getCurrencyRates()
    }

    private fun getBankDepartmentDto(): BankDepartmentDto {
        val bankDto = TestUtils().readXml("xml/currency_rates_cash_main_dep.xml", BankDepartmentDto::class.java)
        return bankDto
    }

}
