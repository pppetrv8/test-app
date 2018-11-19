package com.example.pppetrv.testapplication.ui.model

import android.arch.lifecycle.ViewModel
import com.example.pppetrv.testapplication.util.rx.SchedulerProvider
import com.example.pppetrv.testapplication.store.repository.Repository
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

open class BaseViewModel<V>(val repository: Repository, val schedulerProvider: SchedulerProvider): ViewModel() {

    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    var mView: WeakReference<V>? = null

    fun setView(view: V) {
        this.mView = WeakReference(view)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}