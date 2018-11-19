package com.example.pppetrv.testapplication.ui.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.pppetrv.testapplication.R
import com.example.pppetrv.testapplication.model.BankDepartment
import com.example.pppetrv.testapplication.model.CurrencyRate
import android.view.LayoutInflater
import com.example.pppetrv.testapplication.App
import com.example.pppetrv.testapplication.util.CurrencyFormat

class CurrencyRatesRecyclerViewAdapter(private var rates: List<CurrencyRate>?)
    : RecyclerView.Adapter<CurrencyRatesRecyclerViewAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val v = LayoutInflater.from(App.appInstance).inflate(R.layout.item_currency_rate, parent, false)
        return PersonViewHolder(v)
    }

    override fun getItemCount(): Int {
        if (rates != null) {
            return rates?.size as Int
        }
        return 0
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val rate = rates?.get(position)
        if (rate != null) {
            holder.currencyName?.text = getCurrencyName(rate)
            holder.currencyBidRate?.text = getRateValueStr(rate, true)
            holder.deltaBidValue?.text = getDeltaStr(rate, true)
            holder.currencyAskRate?.text = getRateValueStr(rate, false)
            holder.deltaAskValue?.text = getDeltaStr(rate, false)

            val iconBuy = getDeltaIcon(rate, true)
            val iconSell = getDeltaIcon(rate, false)
            holder.deltaBidValue?.setCompoundDrawablesWithIntrinsicBounds(iconBuy, 0, 0, 0)
            holder.deltaAskValue?.setCompoundDrawablesWithIntrinsicBounds(iconSell, 0, 0, 0)

            val deltaColorBuy = getDeltaColor(rate, true)
            val deltaColorSell = getDeltaColor(rate, false)
            holder.deltaBidValue?.setTextColor(deltaColorBuy)
            holder.deltaAskValue?.setTextColor(deltaColorSell)
        }
    }

    private fun getRateValueStr(rate: CurrencyRate, buy: Boolean): String {
        val scale = rate.buyGoodScale
        val amount = if (buy) {
            rate.buyAmount * scale
        } else {
            rate.sellAmount * scale
        }
        return getAmountValueStr(amount)
    }

    private fun getAmountValueStr(amount: Double): String {
        return CurrencyFormat.format(amount, true)
    }

    private fun getDeltaStr(rate: CurrencyRate, buy: Boolean): String {
        val amount = if (buy) {
            rate.buyAmountDelta
        } else {
            rate.sellAmountDelta
        }
        return getAmountValueStr(amount)
    }

    private fun getCurrencyName(rate: CurrencyRate): String {
        val buyRateStr = rate.buyGoodName?.name
        val sellRateStr = rate.sellGoodName?.name
        val scaleStr = if (rate.buyGoodScale > 1) {
            CurrencyFormat.format(rate.buyGoodScale, false)
        } else { "" }
        return "$scaleStr $buyRateStr / $sellRateStr"
    }

    private fun getDeltaIcon(rate: CurrencyRate, buy: Boolean): Int {
        val delta = if (buy) {
            rate.buyAmountDelta
        } else {
            rate.sellAmountDelta
        }
        return getDeltaIcon(delta)
    }

    private fun getDeltaIcon(value: Double): Int {
        return when {
            value < 0 -> R.drawable.ic_arrow_down_red
            value > 0 -> R.drawable.ic_arrow_up_green
            else -> 0
        }
    }

    private fun getDeltaColor(rate: CurrencyRate, buy: Boolean): Int {
        val delta = if (buy) {
            rate.buyAmountDelta
        } else {
            rate.sellAmountDelta
        }
        return getDeltaColor(delta)
    }

    private fun getDeltaColor(value: Double): Int {
        return when {
            value < 0 -> App.appInstance?.resources?.getColor(R.color.colorPriceDown) as Int
            value > 0 -> App.appInstance?.resources?.getColor(R.color.colorPriceUp) as Int
            else -> Color.TRANSPARENT
        }
    }

    class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var currencyName: TextView? = itemView.findViewById(R.id.currencyName)
        var currencyBidRate: TextView? = itemView.findViewById(R.id.currencyBidRate)
        var deltaBidValue: TextView? = itemView.findViewById(R.id.deltaBidValue)
        var currencyAskRate: TextView? = itemView.findViewById(R.id.currencyAskRate)
        var deltaAskValue: TextView? = itemView.findViewById(R.id.deltaAskValue)
    }
}
