package com.example.pppetrv.testapplication.net

import com.example.pppetrv.testapplication.model.BankDepartment
import com.example.pppetrv.testapplication.net.assembler.BankDepartmentAssembler
import com.example.pppetrv.testapplication.net.dto.BankDepartmentDto


class DtoConverter {

    companion object {
        @JvmStatic private var convInstance: DtoConverter? = null
        fun getConverter(): DtoConverter? {
            if (convInstance == null) {
                convInstance = DtoConverter()
            }
            return convInstance
        }
    }

    fun toBankDepartment(dto: BankDepartmentDto): BankDepartment {
        val assembler = BankDepartmentAssembler.getAssembler()
        val result = assembler?.assemble(dto)
        return result as BankDepartment
    }
}