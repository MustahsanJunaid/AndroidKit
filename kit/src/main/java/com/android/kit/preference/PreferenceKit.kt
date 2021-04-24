package com.android.kit.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.android.kit.model.NightMode
import com.google.gson.Gson

object PreferenceKit {
    private const val PREFERENCE_NAME = "AndroidKit.Preference"
    private const val NIGHT_MODE = "Pref.NightMode"

    private var gson = Gson()
    private lateinit var preference: SharedPreferences

    fun init(context: Context) {
        preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    var nightMode: NightMode = NightMode("Off", AppCompatDelegate.MODE_NIGHT_NO)
        set(value) {
            field = value
            set(NIGHT_MODE, gson.toJson(value, NightMode::class.java))
        }
        get() {
            val str = getString(NIGHT_MODE)
            return if (str == null) {
                NightMode("Off", AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                gson.fromJson(str, NightMode::class.java)
            }
        }

    fun set(key: String, value: Int, commit: Boolean = true) {
        preference.edit(commit) {
            putInt(key, value)
        }
    }

    fun set(key: String, value: String, commit: Boolean = true) {
        preference.edit(commit) {
            putString(key, value)
        }
    }

    fun set(key: String, value: Long, commit: Boolean = true) {
        preference.edit(commit) {
            putLong(key, value)
        }
    }

    fun set(key: String, value: Boolean, commit: Boolean = true) {
        preference.edit(commit) {
            putBoolean(key, value)
        }
    }

    fun remove(key: String) {
        preference.edit {
            remove(key)
        }
    }

    fun contains(key: String): Boolean {
        return preference.contains(key)
    }

    fun getString(key: String): String? {
        return preference.getString(key, null)
    }

    fun getInt(key: String): Int {
        return preference.getInt(key, 0)
    }

    fun getLong(key: String): Long {
        return preference.getLong(key, 0)
    }
}