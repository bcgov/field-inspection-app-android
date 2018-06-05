package io.freshworks.eao

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.parse.Parse
import com.parse.ParseObject
import com.parse.ParseUser
import io.freshworks.eao.data.model.Inspection
import io.freshworks.eao.data.model.Project
import io.freshworks.eao.data.sharedprefs.SharedPrefs
import io.freshworks.eao.screens.login.LoginActivity
import java.util.concurrent.TimeUnit


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ParseObject.registerSubclass(Project::class.java)
        ParseObject.registerSubclass(Inspection::class.java)
        Parse.initialize(Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.parse_application_id))
                .clientKey(getString(R.string.parse_client_key))
                .server(getString(R.string.parse_server_url))
                .enableLocalDataStore()
                .build()
        )

        val sharedPrefs = SharedPrefs(getSharedPreferences(
                "io.freshworks.eao.PREFERENCES_FILE_KEY"
                , Context.MODE_PRIVATE
        ))

        val tracker = BaseAppActivityLifecycleTracker(sharedPrefs)
        registerActivityLifecycleCallbacks(tracker)
    }


    class BaseAppActivityLifecycleTracker(
            private val sharedPrefs: SharedPrefs
    ) : ActivityLifecycleCallbacks {

        private val sessionTimeout = TimeUnit.MINUTES.toMillis(5)

        override fun onActivityStarted(p0: Activity?) {}

        override fun onActivityDestroyed(p0: Activity?) {}

        override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {}

        override fun onActivityStopped(p0: Activity?) {}

        override fun onActivityCreated(p0: Activity?, p1: Bundle?) {}

        override fun onActivityPaused(activity: Activity?) {
            sharedPrefs.onPauseTime = System.currentTimeMillis()
        }

        override fun onActivityResumed(activity: Activity) {
            if (sharedPrefs.onPauseTime < 0) return

            val timeOutOfApp = System.currentTimeMillis() - sharedPrefs.onPauseTime
            if (timeOutOfApp > sessionTimeout) {
                logoutAndRestartApp(activity)
            }
        }

        private fun logoutAndRestartApp(activity: Activity) {
            ParseUser.logOutInBackground()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(intent)
        }

    }
}