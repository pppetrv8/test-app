package com.example.pppetrv.testapplication.net.assembler

import com.example.pppetrv.testapplication.model.BankDepartment
import com.example.pppetrv.testapplication.net.dto.BankDepartmentDto

class BankDepartmentAssembler() : DtoAssembler<BankDepartment, BankDepartmentDto> {

    companion object {
        @JvmStatic private var assemblerInstance: BankDepartmentAssembler? = null

        fun getAssembler(): BankDepartmentAssembler? {
            if (assemblerInstance == null) {
                assemblerInstance = BankDepartmentAssembler()
            }
            return assemblerInstance
        }
    }

    override fun assemble(source: BankDepartmentDto): BankDepartment {
        val bankDepartment = BankDepartment()
        bankDepartment.bankId = source.id
        bankDepartment.name = source.name
        bankDepartment.description = source.description
        bankDepartment.timeWork = source.timeWork
        bankDepartment.number = source.number
        bankDepartment.latitude = source.latitude
        bankDepartment.longitude = source.longitude
        bankDepartment.previousTime = source.previousTime
        bankDepartment.nextTime = source.nextTime
        bankDepartment.fromTime = source.fromTime
        bankDepartment.nationalCurrency = source.nationalCurrency
        val assembler = CurrencyRateAssembler()
        bankDepartment.currencyRates = assembler.assemble(source.currencyRates)
        return bankDepartment
    }
}
