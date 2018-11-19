package com.example.pppetrv.testapplication.net.assembler

import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.net.dto.CurrencyRateDto

class CurrencyRateAssembler: DtoAssembler<List<CurrencyRate>, List<CurrencyRateDto>> {

    override fun assemble(source: List<CurrencyRateDto>): List<CurrencyRate> {
        val ratesList = ArrayList<CurrencyRate>()
        for (dto in source) {
            val rate = assembleCurrencyRate(dto)
            ratesList.add(rate)
        }
        return ratesList
    }

    private fun assembleCurrencyRate(dto: CurrencyRateDto): CurrencyRate {
        var curRate = CurrencyRate()
        curRate.buyGoodId = dto.buyGoodId
        curRate.sellGoodId = dto.sellGoodId
        curRate.buyAmount = dto.buyAmount
        curRate.sellAmount = dto.sellAmount
        curRate.sellAmountDelta = dto.sellAmountDelta
        curRate.buyAmountDelta = dto.buyAmountDelta
        curRate.buyGoodName = dto.buyGoodName
        curRate.sellGoodName = dto.sellGoodName
        curRate.buyGoodTitle = dto.buyGoodTitle
        curRate.sellGoodTitle = dto.sellGoodTitle
        curRate.itemOrder = dto.itemOrder
        curRate.buyGoodScale = dto.buyGoodScale
        return curRate
    }
}