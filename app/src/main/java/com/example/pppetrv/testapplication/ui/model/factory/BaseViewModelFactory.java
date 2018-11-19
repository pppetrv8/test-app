package com.example.pppetrv.testapplication.ui.model.factory;

import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.pppetrv.testapplication.App;
import com.example.pppetrv.testapplication.ui.model.BaseViewModel;
import com.example.pppetrv.testapplication.util.rx.SchedulerProvider;
import com.example.pppetrv.testapplication.store.repository.Repository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public abstract class BaseViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    protected SchedulerProvider schedulerProvider;
    @Inject
    protected Repository repository;

    public BaseViewModelFactory() {
        App.getComponent().inject(this);
    }

    @NonNull
    @Override
    public abstract BaseViewModel create(@NonNull Class modelClass);
}
