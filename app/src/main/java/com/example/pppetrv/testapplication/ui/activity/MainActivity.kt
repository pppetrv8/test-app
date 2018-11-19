package com.example.pppetrv.testapplication.ui.activity

import android.os.Bundle
import com.example.pppetrv.testapplication.R
import com.example.pppetrv.testapplication.ui.fragment.CurrencyRatesListFragment
import com.example.pppetrv.testapplication.databinding.ActivityMainBinding

class MainActivity : BaseFragmentActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setToolBar(toolbar)
        replaceFragment(CurrencyRatesListFragment(), null)
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }
}
