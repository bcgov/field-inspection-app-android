package io.freshworks.eao.data.sharedprefs

import android.content.SharedPreferences

class SharedPrefs(val sharedPreferences: SharedPreferences) {

    val ON_PAUSE_TIME = "on_pause_time"

    var onPauseTime: Long
    get() = sharedPreferences.getLong(ON_PAUSE_TIME, -1L)
    set(value) = sharedPreferences.edit().putLong(ON_PAUSE_TIME, value).apply()

}