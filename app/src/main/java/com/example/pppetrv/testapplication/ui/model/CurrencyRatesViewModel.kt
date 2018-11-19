package com.example.pppetrv.testapplication.ui.model

import android.arch.lifecycle.MutableLiveData
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.util.rx.SchedulerProvider
import com.example.pppetrv.testapplication.store.repository.Repository
import com.example.pppetrv.testapplication.ui.model.view.CurrencyRatesView
import com.example.pppetrv.testapplication.util.ConnectivityUtils
import com.example.pppetrv.testapplication.util.Constants

class CurrencyRatesViewModel(repository: Repository, schedulerProvider: SchedulerProvider):
    BaseViewModel<CurrencyRatesView>(repository, schedulerProvider) {

    val liveData = MutableLiveData<List<CurrencyRate>>()
    private var lastError: Throwable? = null

    private var useCache = !ConnectivityUtils.isNetworkConnected()
    val bankId = Constants.CURRENCY_MAIN_TRADER

    fun getError(): Throwable? {
        return lastError
    }

    fun refresh() {
        mCompositeDisposable.clear()
        mCompositeDisposable.add(repository.getCurrencyRates(bankId, useCache).
            subscribeOn(schedulerProvider.io()).
            observeOn(schedulerProvider.ui()).
            subscribe(liveData::postValue))
    }
}