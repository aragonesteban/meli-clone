package com.meli.shared.extensions

import java.text.NumberFormat
import java.util.*

private const val CURRENCY_CODE = "USD"

fun Int.formatToCurrency(): String {
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 0
    format.currency = Currency.getInstance(CURRENCY_CODE)

    return format.format(this)
}