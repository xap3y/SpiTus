package me.xap3y.spitus.Utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import me.xap3y.spitus.MainActivity

class DataManager internal constructor(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("SpiTus", Context.MODE_PRIVATE)

    companion object {
        private var instance: DataManager? = null

        fun getInstance(context: Context): DataManager {
            if (instance == null) {
                instance = DataManager(context)
            }
            return instance!!
        }
    }

    fun saveString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String? = null): String {
        if (defaultValue != null) return preferences.getString(key, defaultValue) ?: defaultValue
        return preferences.getString(key, "null") ?: "null"
    }

    fun getBool(key: String, defaultValue: Boolean? = false): Boolean {
        if (defaultValue != null) return preferences.getBoolean(key, defaultValue)
        return preferences.getBoolean(key, false)
    }

    fun setBool(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }
}
