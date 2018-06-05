package io.freshworks.eao.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.toLongCanadianDateString() : String {
    val sdf =  SimpleDateFormat.getDateInstance(DateFormat.LONG, Locale.CANADA)
    return sdf.format(this)
}