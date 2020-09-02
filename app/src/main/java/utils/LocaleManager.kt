package utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Build.VERSION_CODES.N
import android.preference.PreferenceManager
import android.util.Log
import fungsiu.kotlin.R
import java.util.*

object LocaleManager {
    val LANGUAGE_ENGLISH = "en"
    val LANGUAGE_TRADITIONAL_CHINESE = "tc"
    val LANGUAGE_SIMPLIFIED_CHINESE = "sc"
    private val LANGUAGE_KEY = ".LANGUAGE"

    fun setLocale(ctx: Context): Context {
        return updateResources(ctx, getLanguage(ctx))
    }

    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(c, language)
        return updateResources(c, language)
    }

    fun getLanguage(ctx: Context): String {
        val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
        val lang = prefs.getString(LANGUAGE_KEY, ctx.packageName + LANGUAGE_ENGLISH)
        Log.i(javaClass.name, "language : $lang")
        return lang.toString()
    }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(ctx: Context, language: String) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
        // use commit() instead of apply(), because sometimes we kill the application process immediately
        // which will prevent apply() to finish
        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun updateResources(ctx: Context, lang: String): Context {
        var context = ctx
        val language: String
        val country: String
        when (lang) {
            LANGUAGE_ENGLISH -> {
                language = "en"
                country = "US"
            }
            LANGUAGE_TRADITIONAL_CHINESE -> {
                language = "zh"
                country = "HK"
            }
            LANGUAGE_SIMPLIFIED_CHINESE -> {
                language = "zh"
                country = "CN"
            }
            else -> {
                language =  context.resources.getString(R.string.language)
                country =  context.resources.getString(R.string.country)
            }
        }
        val locale = Locale(language, country)
        Locale.setDefault(locale)

        val resources = context.resources
        val config = Configuration(resources.configuration)
//        if (Build.VERSION.SDK_INT >= 17) {
//            config.setLocale(locale)
//            context = context.createConfigurationContext(config)
//        } else {
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
//        }
        return context
    }

    fun getLocale(ctx: Context): Locale {
        val config = ctx.resources.configuration
        return if (Build.VERSION.SDK_INT >= N) {
            config.locales.get(0)
        } else {
            config.locale
        }
    }

}