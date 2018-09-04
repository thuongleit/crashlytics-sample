package me.thuongle.crashlyticssample

import android.app.Application
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import io.fabric.sdk.android.Fabric
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val crashlytics = CrashlyticsCore.Builder()
                .disabled(BuildConfig.DEBUG)
                .build()
        Fabric.with(this, Crashlytics.Builder().core(crashlytics).build())

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.plant(CrashlyticsTree())
    }

    inner class CrashlyticsTree : Timber.Tree() {

        private val KEY_PRIORITY = "priority"
        private val KEY_TAG = "tag"
        private val KEY_MESSAGE = "message"

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            when (priority) {
                Log.VERBOSE, Log.DEBUG, Log.INFO -> return

                else -> {
                    Crashlytics.setInt(KEY_PRIORITY, priority)
                    Crashlytics.setString(KEY_TAG, tag)
                    Crashlytics.setString(KEY_MESSAGE, message)

                    if (t == null) {
                        Crashlytics.logException(Exception(message))
                    } else {
                        Crashlytics.logException(t)
                    }
                }
            }
        }
    }
}
