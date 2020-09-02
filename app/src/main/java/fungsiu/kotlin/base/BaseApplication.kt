package fungsiu.kotlin.base

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.elvishew.xlog.LogLevel
import com.elvishew.xlog.XLog
import com.facebook.stetho.Stetho
import fungsiu.kotlin.BuildConfig
import utils.LocaleManager


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        initChromeInspect()
        initXLog()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.setLocale(this)
    }

    private fun initChromeInspect() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initXLog() {
        XLog.init(if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE)
    }

    companion object {
        private lateinit var instance: Application
        fun instance() = instance
    }


}