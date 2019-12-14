package com.tia.my.app.decl_tweet

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


public val PREF_NAME = "prefs"

public val PREF_TEMPLATE_PREPEND = "template_prepend"
public val PREF_TEMPLATE_DOTS = "template_dots"
public val PREF_TEMPLATE_DONE_PREPEND = "template_done_prepend"
public val PREF_LAST_DONE_DATE = "done_date"

public val PREF_DEFAULT_PREPEND = "DEFAULT PREPEND"
public val PREF_DEFAULT_DOTS = "DEFAULT DOTS"
public val PREF_DEFAULT_DONE_PREPEND = "DEFAULT DONE PREPEND"


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val prefs = getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        editPrepend.setText(prefs.getString(PREF_TEMPLATE_PREPEND, PREF_DEFAULT_PREPEND))
        editDots.setText(prefs.getString(PREF_TEMPLATE_DOTS, PREF_DEFAULT_DOTS))
        editDonePrepend.setText(prefs.getString(PREF_TEMPLATE_DONE_PREPEND, PREF_DEFAULT_DONE_PREPEND))

        startServiceButton.setOnClickListener(View.OnClickListener {
            startService(Intent(baseContext, TestService::class.java))
        })

        StopServiceButton.setOnClickListener(View.OnClickListener {
            stopService(Intent(baseContext, TestService::class.java))
        })

        editPrepend.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val e = prefs.edit()
                e.putString(PREF_TEMPLATE_PREPEND, p0.toString())
                e.apply()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        editDots.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val e = prefs.edit()
                e.putString(PREF_TEMPLATE_DOTS, p0.toString())
                e.apply()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        editDonePrepend.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                val e = prefs.edit()
                e.putString(PREF_TEMPLATE_DONE_PREPEND, p0.toString())
                e.apply()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        resetButton.setOnClickListener(View.OnClickListener {
            val a = prefs.edit()
            a.putString(PREF_LAST_DONE_DATE, "")
            a.apply()
        })

        testButton.setOnClickListener(View.OnClickListener {

//            val notificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//            val name = "decl_tweet"
//            val id = "test-id-1"
//            val notifiDescription = "desc"
//            val channel_name = "test-channel"
//
//
//            if (Build.VERSION.SDK_INT >= 26) {
//
//                if (notificationManager.getNotificationChannel(channel_name) == null) {
//                    val channel = NotificationChannel(
//                        "channel-id",
//                        "channelのid",
//                        NotificationManager.IMPORTANCE_DEFAULT
//                    )
//                    channel.apply {
//                        description = notifiDescription
//                    }
//                    channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
//
//                    notificationManager.createNotificationChannel(channel)
//                }
//
//                val notifi = NotificationCompat.Builder(this, id).apply {
//                    setSmallIcon(R.drawable.ic_launcher_background)
//                    setContentTitle("titleeeee")
//                    setContentText("contentttttttttttttt")
//                    setAutoCancel(false)
//                    setChannelId(id)
//                }.build()
//
//                notifi.flags = Notification.FLAG_NO_CLEAR
//
//                notificationManager.notify(99, notifi)
//            } else {
//                val notifi = NotificationCompat.Builder(this, id).apply {
//                    setSmallIcon(R.drawable.ic_launcher_background)
//                }.build()
//
//                notifi.flags = Notification.FLAG_NO_CLEAR
//
//                notificationManager.notify(99, notifi)
//            }
        })
    }
}


fun Date.toDateString(pattern: String = "yyyy/MM/dd"): String? {
    val sdFormat = try {
        SimpleDateFormat(pattern)
    } catch (e: IllegalArgumentException) {
        null
    }

    val str = sdFormat?.format(this)

    return str
}
