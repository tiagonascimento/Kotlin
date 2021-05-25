package com.tiagorocha.tasks.service.repository.local

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {
    private val _preferences: SharedPreferences =
        context.getSharedPreferences("taskShared", Context.MODE_PRIVATE)

    fun store(key: String, value: String) {
        _preferences.edit().putString(key, value).apply()
    }

    fun remove(key: String) {
        _preferences.edit().remove(key)
    }

    fun get(key: String): String {
        return _preferences.getString(key, "") ?: ""
    }

}