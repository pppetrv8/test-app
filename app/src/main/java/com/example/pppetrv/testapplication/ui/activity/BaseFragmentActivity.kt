package com.example.pppetrv.testapplication.ui.activity

import android.os.Build
import android.os.Bundle
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.example.pppetrv.testapplication.R
import com.example.pppetrv.testapplication.ui.fragment.BaseFragment
import com.example.pppetrv.testapplication.ui.view.ProgressHelper

open abstract class BaseFragmentActivity<Binding: ViewDataBinding> : AppCompatActivity() {

    var progressHelper: ProgressHelper? = null
    protected lateinit var viewDataBinding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressHelper = ProgressHelper(supportFragmentManager)
        setContentView(getLayoutResId())
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutResId())

    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    fun fragmentContainer(): Int {
        return R.id.contentLayout
    }

    fun replaceFragment(fragment: BaseFragment<out ViewDataBinding>, bundle: Bundle?) {
        var destroyedFlag = true
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 >= Build.VERSION.SDK_INT) {
            destroyedFlag = isDestroyed
        }
        takeUnless { isFinishing && destroyedFlag }?.supportFragmentManager?.inTransaction {
            replace(fragmentContainer(), fragment.also { fragment.arguments = bundle }, fragment.javaClass.name)
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun showProgressDialog(isCancelable: Boolean) {
        progressHelper?.showProgressDialog(isCancelable)
    }

    fun hideProgressDialog() {
        progressHelper?.hideProgressDialog()
    }

    fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(fragmentContainer())
    }

    fun setToolBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    fun getActionBarSupport(): ActionBar? {
        return super.getSupportActionBar()
    }
}
