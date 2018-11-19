package com.example.pppetrv.testapplication.model

import com.example.pppetrv.testapplication.R

import android.content.Context

enum class AccountCurrency constructor(val code: Int, private val resId: Int) {

    BYR(974, R.string.BYR),
    BYN(933, R.string.BYR),
    EUR(978, R.string.EUR),
    USD(840, R.string.USD),
    UAH(980, R.string.UAH),
    CHF(756, R.string.CHF),
    SEK(752, R.string.SEK),
    ZAR(710, R.string.ZAR),
    SGD(702, R.string.SGD),
    RUB(643, R.string.RUB),
    PLN(985, R.string.PLN),
    NOK(578, R.string.NOK),
    NZD(554, R.string.NZD),
    MXN(484, R.string.MXN),
    LTL(440, R.string.LTL),
    LVL(428, R.string.LVL),
    JPY(392, R.string.JPY),
    EEK(233, R.string.EEK),
    CZK(203, R.string.CZK),
    CAD(124, R.string.CAD),
    DKK(208, R.string.DKK),
    GBP(826, R.string.GBP),
    AUD(30, R.string.AUD),
    ISK(352, R.string.ISK),
    BGN(975, R.string.BGN),
    KZT(398, R.string.KZT),
    KWD(414, R.string.KWD),
    KGS(417, R.string.KGS),
    MDL(498, R.string.MDL),
    TRL(792, R.string.TRL),
    XDR(960, R.string.XDR);

    fun getName(context: Context): String {
        return context.getString(resId)
    }

    companion object {

        fun getValue(code: Int): AccountCurrency? {
            for (c in values()) {
                if (c.code == code) {
                    return c
                }
            }
            return null
        }

        fun getValue(name: String?): AccountCurrency? {
            for (c in values()) {
                if (c.name == name) {
                    return c
                }
            }
            return null
        }
    }
}
