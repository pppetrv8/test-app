package com.example.pppetrv.testapplication.ui.view

import android.support.v4.app.FragmentManager
import com.example.pppetrv.testapplication.ui.dialog.ProgressDialogFragment
import com.example.pppetrv.testapplication.util.Constants.TAG_PROGRESS_DIALOG
import timber.log.Timber

class ProgressHelper(fragmentManager: FragmentManager) {

    private var progressDialogFragment: ProgressDialogFragment? = null
    private var mFragmentManager: FragmentManager = fragmentManager


    fun showProgressDialog(isCancelable: Boolean) {
        Timber.d("showProgressDialog ")
        progressDialogFragment = mFragmentManager.findFragmentByTag(TAG_PROGRESS_DIALOG) as ProgressDialogFragment?
        if (progressDialogFragment == null) {
            progressDialogFragment = ProgressDialogFragment()
        }
        if (progressDialogFragment!!.isVisible || progressDialogFragment!!.isAdded) {
            return
        }
        progressDialogFragment?.isCancelable = isCancelable
        progressDialogFragment?.show(mFragmentManager, TAG_PROGRESS_DIALOG)
    }

    fun hideProgressDialog() {
        Timber.d("hideProgressDialog ")
        dismissDialogAllowingStateLoss(TAG_PROGRESS_DIALOG)
    }

    private fun dismissDialogAllowingStateLoss(tag: String) {
        if (progressDialogFragment != null) {
            progressDialogFragment?.dismissAllowingStateLoss()
            progressDialogFragment = null
        }
    }
}