package com.example.pppetrv.testapplication.ui.model.factory;

import android.support.annotation.NonNull;
import com.example.pppetrv.testapplication.ui.model.BaseViewModel;
import com.example.pppetrv.testapplication.ui.model.CurrencyRatesViewModel;

public class CurrencyRatesViewModelFactory extends BaseViewModelFactory {

    @NonNull
    @Override
    public BaseViewModel create(@NonNull Class modelClass) {
        return new CurrencyRatesViewModel(repository, schedulerProvider);
    }
}

