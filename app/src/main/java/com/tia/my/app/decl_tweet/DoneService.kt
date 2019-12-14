package com.tia.my.app.decl_tweet

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity

class DoneService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
        super.onStartCommand(intent, flags, startId)

        val prefs = getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val index = intent?.getIntExtra("index", -1)

        println(index)
        println(intent)
        println(intent?.extras)

        if (index == -1) {
            stopSelf()
            return Service.START_STICKY_COMPATIBILITY
        }

        notificationManager.cancel(index!!)

        val intent = Intent()
        intent.setAction(Intent.ACTION_VIEW)
        var schemeUri = "twitter://post"

        val message = prefs.getString("todo-${index}", "error").toString()
        val prepend = prefs.getString(PREF_TEMPLATE_DONE_PREPEND, PREF_DEFAULT_DONE_PREPEND).toString()

        intent.setData(Uri.parse(schemeUri + "?message=" + prepend + message))
        startActivity(intent)
        stopSelf()

        return Service.START_STICKY_COMPATIBILITY
    }

}
