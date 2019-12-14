package com.tia.my.app.decl_tweet

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TestService : Service() {


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        println("onCreate...")
        registerReceiver(TestBroadcastReceiver(), IntentFilter(Intent.ACTION_USER_PRESENT))
    }

    public inner class TestBroadcastReceiver : BroadcastReceiver() {

        val prefs = getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        override fun onReceive(p0: Context?, p1: Intent?) {
            println("get Present!")

            val last_date = prefs.getString(PREF_LAST_DONE_DATE, "")
            if (last_date != Date().toDateString()) {
                val c = prefs.getInt("todo-count", 0)

                val edit = prefs.edit()
                for (i in 0..c-1) {
                    edit.remove("todo-${i}")
                }
                edit.apply()

                p0!!.startActivity(Intent(p0, EditTweetActivity::class.java))
            }
        }
    }
}
