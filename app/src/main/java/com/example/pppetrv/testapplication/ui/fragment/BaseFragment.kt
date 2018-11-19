package com.example.pppetrv.testapplication.ui.fragment

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pppetrv.testapplication.App
import com.example.pppetrv.testapplication.store.repository.Repository
import com.example.pppetrv.testapplication.ui.activity.BaseFragmentActivity
import com.example.pppetrv.testapplication.util.ActivityUtil
import javax.inject.Inject

open abstract class BaseFragment<Binding: ViewDataBinding> : Fragment() {

    protected lateinit var viewDataBinding: Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), null, false)
        return viewDataBinding.root
    }

    abstract fun getLayoutId(): Int

    fun showToast(msgId: Int) {
        val msgStr = getString(msgId)
        showToast(msgStr)
    }

    fun showToast(msgStr: String?) {
        ActivityUtil.runOnMainThread(Runnable {
            Toast.makeText(App.appInstance, msgStr, Toast.LENGTH_SHORT).show()
        })
    }

    fun getBaseActivity(): BaseFragmentActivity<*>? {
        if (activity != null && activity is BaseFragmentActivity<*>) {
            return activity as BaseFragmentActivity<*>
        }
        return null
    }

    fun showProgressDialog(isCancelable: Boolean) {
        getBaseActivity()?.showProgressDialog(isCancelable)
    }

    fun hideProgressDialog() {
        getBaseActivity()?.hideProgressDialog()
    }

    fun setTitle(titleId: Int) {
        val titleStr = getString(titleId)
        setTitle(titleStr)
    }

    fun setTitle(title: String) {
        getBaseActivity()?.supportActionBar?.title = title
    }

    fun setToolBar(toolbar: Toolbar) {
        getBaseActivity()?.setToolBar(toolbar)
    }
}
