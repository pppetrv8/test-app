package com.example.pppetrv.testapplication.store.db.room;

import android.arch.persistence.room.TypeConverter;

import com.example.pppetrv.testapplication.model.AccountCurrency;

import java.util.Date;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromAccountCurrency(AccountCurrency currency) {
        return currency.name();
    }

    @TypeConverter
    public static AccountCurrency toAccountCurrency(String value) {
        return AccountCurrency.Companion.getValue(value);
    }
}

