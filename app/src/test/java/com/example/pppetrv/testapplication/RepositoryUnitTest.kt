package com.example.pppetrv.testapplication

import com.example.pppetrv.testapplication.store.repository.Repository
import com.example.pppetrv.testapplication.util.Constants
import com.example.pppetrv.testapplication.util.rx.SchedulerProvider

import org.junit.Test
import org.junit.Before
import javax.inject.Inject
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.net.DtoConverter
import com.example.pppetrv.testapplication.net.NetService
import com.example.pppetrv.testapplication.net.dto.BankDepartmentDto
import com.example.pppetrv.testapplication.util.TestUtils
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber

class RepositoryUnitTest: BaseTest() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var netService: NetService

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    var bankDto: BankDepartmentDto? = null
    val bankId = Constants.CURRENCY_MAIN_TRADER

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        component?.inject(this)
    }

    @Test
    fun checkRepositoryTest() {
        assert(repository != null)
        assert(netService != null)
    }

    @Test
    fun getCurrencyRatesNetTest() {

        System.out.println("started checkBankDepartmentNetTest...")

        val testSubscriber = TestSubscriber<List<CurrencyRate>>()
        getCurrencyRates(bankId).subscribeOn(schedulerProvider.io())
           .observeOn(schedulerProvider.ui())
           .subscribe(testSubscriber)

        testSubscriber.assertNoErrors()
        checkDataItemsCount(testSubscriber)

        val values = testSubscriber.values()
        System.out.println("ended checkBankDepartmentNetTest, data: $values")
    }

    private fun checkDataItemsCount(testSubscriber: TestSubscriber<List<CurrencyRate>>) {
        var count = 0
        bankDto?.let {
            count = it.ratesCount()
        }
        val values = testSubscriber.values()
        assert(values.size > 0 && values[0] != null && values[0].size == count)

    }

    private fun getBankDepartmentDto(): BankDepartmentDto {
        val bankDto = TestUtils().readXml("xml/currency_rates_cash_main_dep.xml", BankDepartmentDto::class.java)
        return bankDto
    }

    private fun getCurrencyRates(bankId: Int) : Flowable<List<CurrencyRate>> {
        bankDto = getBankDepartmentDto()
        val converter = DtoConverter.getConverter()
        var rates: List<CurrencyRate> = ArrayList()
        bankDto?.let {
            val result = converter?.toBankDepartment(it)
            rates = result?.currencyRates as List<CurrencyRate>

        }
        return Flowable.fromCallable{ rates }
    }
}
