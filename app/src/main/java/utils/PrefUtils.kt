package utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import fungsiu.kotlin.base.BaseApplication

object PrefUtils {
    val prefName = BaseApplication.instance().packageName + ".pref"

    fun savePref(ctx: Context, key: String, value: Any) {
        val pref = ctx.getSharedPreferences(prefName, MODE_PRIVATE)
        when (value) {
            is Boolean -> {
                pref.edit().putBoolean(key, value).apply()
            }
            is Float -> {
                pref.edit().putFloat(key, value).apply()
            }
            is Int -> {
                pref.edit().putInt(key, value).apply()
            }
            is Long -> {
                pref.edit().putLong(key, value).apply()
            }
            is String -> {
                pref.edit().putString(key, value).apply()
            }
        }
    }

    fun getPrefBoolean(ctx: Context, key: String, defaultValue: Boolean): Boolean {
        return ctx.getSharedPreferences(prefName, MODE_PRIVATE)
                .getBoolean(key, defaultValue)
    }

    fun getPrefString(ctx: Context, key: String, defaultValue: String): String? {
        return ctx.getSharedPreferences(prefName, MODE_PRIVATE)
                .getString(key, defaultValue)
    }

    fun removePref(ctx: Context, key: String) {
        ctx.getSharedPreferences(prefName, MODE_PRIVATE).edit().remove(key).apply()
    }

    fun removeAllPref(ctx: Context) {
        ctx.getSharedPreferences(prefName, MODE_PRIVATE).edit().clear().apply()
    }

}