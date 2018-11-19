package com.example.pppetrv.testapplication.ui.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.example.pppetrv.testapplication.R
import com.example.pppetrv.testapplication.extension.notNull

class ProgressDialogFragment: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window.notNull { it.requestFeature(Window.FEATURE_NO_TITLE) }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        dialog.window.notNull { it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }
    }
}