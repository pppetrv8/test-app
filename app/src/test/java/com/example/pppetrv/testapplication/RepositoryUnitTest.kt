package com.example.pppetrv.testapplication

import com.example.pppetrv.testapplication.store.repository.Repository
import com.example.pppetrv.testapplication.util.Constants
import com.example.pppetrv.testapplication.util.rx.SchedulerProvider

import org.junit.Test
import org.junit.Before
import javax.inject.Inject
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.net.NetService
import com.example.pppetrv.testapplication.net.dto.BankDepartmentDto
import com.example.pppetrv.testapplication.store.db.DbProvider
import com.example.pppetrv.testapplication.util.TestUtils
import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import java.util.concurrent.TimeUnit

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class RepositoryUnitTest: BaseTest() {

    @Inject
    lateinit var repository: Repository

    @Inject
    lateinit var netService: NetService

    @Inject
    lateinit var dbProvider: DbProvider

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
        assert(dbProvider != null)
    }

    @Test
    fun getCurrencyRatesNetTest() {
        println("started CurrencyRatesNetTest...")

        val testSubscriber = TestSubscriber<List<CurrencyRate>>()
        val ratesFlowNet = getCurrencyRatesFlowable(false)
        ratesFlowNet.subscribe(testSubscriber)

        testSubscriber.awaitDone(2, TimeUnit.MINUTES)
        testSubscriber.assertNoErrors()

        val values = testSubscriber.values()
        println("ended CurrencyRatesNetTest, data: $values")

        checkDataItemsCount(testSubscriber)
    }

    @Test
    fun getCurrencyRatesDbTest() {
        println("started CurrencyRatesDbTest...")

        println("1. run net request and store data to DB")
        val testSubscriberNet = TestSubscriber<List<CurrencyRate>>()
        val ratesFlowNet = getCurrencyRatesFlowable(false)
        ratesFlowNet.subscribe(testSubscriberNet)
        testSubscriberNet.awaitDone(10, TimeUnit.SECONDS)

        println("2. get stored data from DB on previous step")
        val testSubscriberDb = TestSubscriber<List<CurrencyRate>>()
        val ratesFlowDb = getCurrencyRatesFlowable(true)
        ratesFlowDb.subscribe(testSubscriberDb)
        testSubscriberDb.awaitDone(5, TimeUnit.SECONDS)

        testSubscriberDb.assertNoErrors()
        val values = testSubscriberDb.values()
        println("ended CurrencyRatesDbTest, data: $values")

        checkDataItemsCount(testSubscriberDb)
    }

    private fun getCurrencyRatesFlowable(useCache: Boolean): Flowable<List<CurrencyRate>> {
        return repository.getCurrencyRates(bankId, useCache)
               .subscribeOn(schedulerProvider.io())
               .observeOn(schedulerProvider.io())
    }

    private fun checkDataItemsCount(testSubscriber: TestSubscriber<List<CurrencyRate>>) {
        var count = 0
        bankDto = getBankDepartmentDto()
        bankDto?.let {
            count = it.ratesCount()
        }
        val values = testSubscriber.values()
        assert(values.size > 0 && values[0] != null && values[0].size == count)
    }

    private fun getBankDepartmentDto(): BankDepartmentDto {
        return TestUtils().readXml("xml/currency_rates_cash_main_dep.xml", BankDepartmentDto::class.java)
    }
}
