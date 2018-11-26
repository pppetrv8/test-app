package com.example.pppetrv.testapplication.util.net

import com.example.pppetrv.testapplication.net.ServiceApi
import com.example.pppetrv.testapplication.net.dto.BankDepartmentDto
import com.example.pppetrv.testapplication.util.TestUtils
import retrofit2.Call
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.Calls

class TestServiceApi: ServiceApi {

    override fun getCurrencyRates(trader: Int): Call<BankDepartmentDto> {
        val response = Response.success(200, getBankDepartmentDto())
        return Calls.response<BankDepartmentDto>(response)
    }

    private fun getBankDepartmentDto(): BankDepartmentDto {
        val bankDto = TestUtils().readXml("xml/currency_rates_cash_main_dep.xml", BankDepartmentDto::class.java)
        println("getBankDepartmentDto: $bankDto")
        return bankDto
    }

}
