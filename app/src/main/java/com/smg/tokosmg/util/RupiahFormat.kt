package com.smg.tokosmg.util

import java.text.NumberFormat
import java.util.Locale

fun rupiahFormat (number: Long) : String {
    val localeID = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    numberFormat.maximumFractionDigits = 0
    return numberFormat.format(number)
}