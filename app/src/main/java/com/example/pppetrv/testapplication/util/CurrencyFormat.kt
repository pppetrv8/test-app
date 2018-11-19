package com.example.pppetrv.testapplication.util

import java.text.DecimalFormat

object CurrencyFormat {

    private var fmtNoFraction: DecimalFormat = DecimalFormat("#.#")
    private var fmtWithFraction: DecimalFormat = DecimalFormat("#0.####")

    fun format(value: Double, fraction: Boolean): String {
        return if (fraction) {
            fmtWithFraction.format(value)
        } else {
            fmtNoFraction.format(value)
        }
    }
}