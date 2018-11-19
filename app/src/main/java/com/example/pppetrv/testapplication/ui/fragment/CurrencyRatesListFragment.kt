package com.example.pppetrv.testapplication.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.pppetrv.testapplication.R
import com.example.pppetrv.testapplication.databinding.FragmentCurrencyRatesBinding
import com.example.pppetrv.testapplication.model.CurrencyRate
import com.example.pppetrv.testapplication.ui.adapter.CurrencyRatesRecyclerViewAdapter
import com.example.pppetrv.testapplication.ui.model.CurrencyRatesViewModel
import com.example.pppetrv.testapplication.ui.model.factory.CurrencyRatesViewModelFactory
import com.example.pppetrv.testapplication.ui.model.view.CurrencyRatesView
import com.example.pppetrv.testapplication.util.ActivityUtil
import kotlinx.android.synthetic.main.fragment_currency_rates.*
import timber.log.Timber

class CurrencyRatesListFragment : BaseFragment<FragmentCurrencyRatesBinding>(), CurrencyRatesView {

    private var adapter: CurrencyRatesRecyclerViewAdapter? = null
    private var ratesModel: CurrencyRatesViewModel?  = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_currency_rates
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        setToolBar(toolbar)
        setTitle(R.string.rates_screen_title)
        ratesModel = ViewModelProviders.of(activity as FragmentActivity, CurrencyRatesViewModelFactory()).get(CurrencyRatesViewModel::class.java)
        ratesModel?.setView(this)
        ratesModel?.liveData?.observe(this, Observer<List<CurrencyRate>> {
            hideProgressDialog()
            if (it == null) {
                Timber.d("rates request, err: ${ratesModel?.getError()}")
            } else {
                fillRecyclerView(it)
            }
        })
        fillRecyclerView(null)
        refreshBtn.setOnClickListener {
            refreshCurrencyRates()
        }
    }

    override fun refreshCurrencyRates() {
        showProgressDialog(true)
        ratesModel?.refresh()
    }

    private fun fillRecyclerView(rates: List<CurrencyRate>?) {
        ActivityUtil.runOnMainThread(Runnable {
            listView.layoutManager = LinearLayoutManager(context)
            adapter = CurrencyRatesRecyclerViewAdapter(rates)
            listView.adapter = adapter
        })
    }
}