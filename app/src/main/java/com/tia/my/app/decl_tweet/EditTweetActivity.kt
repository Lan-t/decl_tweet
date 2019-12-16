package com.tia.my.app.decl_tweet

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.children
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tweet_edit.*
import java.net.URI
import java.util.*

class EditTweetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweet_edit)

        val prefs = getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        var reSet = false

        if (prefs.getString(PREF_LAST_DONE_DATE, "").toString() == Date().toDateString() ) {
            val c = prefs.getInt("todo-count", 0)
            if (c != 0) {
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.cancelAll()

                tweetEditLayout.removeViewAt(tweetEditLayout.childCount - 2)

                for (i in 0..c-1) {
                    var editText = EditText(this)
                    val a = prefs.getString("todo-${i}", "error").toString()
                    val b = Editable.Factory.getInstance().newEditable(a)
                    println("hogehoge" + a)
                    editText.setText(a)
                    tweetEditLayout.addView(editText, tweetEditLayout.childCount - 1)
                }
                reSet = true
            }
        }

        AddButton.setOnClickListener(View.OnClickListener {
            tweetEditLayout.addView(EditText(this), tweetEditLayout.childCount - 1)
            (tweetEditLayout.children.toList()[tweetEditLayout.childCount - 2] as EditText).requestFocus()
        })

        RemoveButton.setOnClickListener(View.OnClickListener {
            tweetEditLayout.removeViewAt(tweetEditLayout.childCount - 2)
        })

        OkButton.setOnClickListener(View.OnClickListener {
            var intent = Intent()
            intent.setAction(Intent.ACTION_VIEW)
            var schemeUri = "twitter://post"
            var prepend = prefs.getString(PREF_TEMPLATE_PREPEND, PREF_DEFAULT_PREPEND) + "\n"
            var dots = prefs.getString(PREF_TEMPLATE_DOTS, PREF_DEFAULT_DOTS)

            var c = 0
            val edit = prefs.edit()
            for (i in tweetEditLayout.children.toList().slice(1..tweetEditLayout.childCount-1)) {
                if (i is TextView) {
                    val text = (i as TextView).text.toString()
                    prepend += dots + text + "\n"

                    edit.putString("todo-${c}", text)

                    c ++
                }
            }
            edit.putInt("todo-count", c)
            edit.putString(PREF_LAST_DONE_DATE, Date().toDateString())
            edit.apply()

            if (reSet) {
                prepend = "修正版\n" + prepend
            }

            intent.setData(Uri.parse(schemeUri + "?message=" + prepend))
            startActivity(intent)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val name = "decl_tweet"
            val id = "test-id-1"
            val notifiDescription = "desc"
            val channel_name = "test-channel"


            if (Build.VERSION.SDK_INT >= 26) {

                if (notificationManager.getNotificationChannel(channel_name) == null) {
                    val channel = NotificationChannel(
                        "todo",
                        "TODO",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    channel.apply {
                        description = notifiDescription
                    }
                    channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC

                    notificationManager.createNotificationChannel(channel)
                }

                for (i in 0..c-1) {

                    val text = prefs.getString("todo-${i}", "error").toString()
                    val doneIntent = Intent(baseContext, DoneService::class.java).apply {
                        putExtra("index", i)
                    }
                    val donePendingIntent = PendingIntent.getService(baseContext, 0, doneIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                    val notifi = NotificationCompat.Builder(this, id).apply {
                        setSmallIcon(R.drawable.ic_launcher_background)
                        setContentTitle("TODO")
                        setContentText(text)
                        setAutoCancel(false)
                        addAction(R.drawable.ic_launcher_background, "Done", donePendingIntent)
                        setChannelId(id)
                    }.build()

                    notifi.flags = Notification.FLAG_NO_CLEAR

                    notificationManager.notify(c, notifi)

                    Thread.sleep(1000)
                }

            } else {

                for (i in 0..c-1) {

                    val text = prefs.getString("todo-${i}", "error").toString()
                    val doneIntent = Intent(baseContext, DoneService::class.java).apply {
                        putExtra("index", i)
                    }
                    val donePendingIntent = PendingIntent.getService(baseContext, i, doneIntent, PendingIntent.FLAG_UPDATE_CURRENT)

                    val notifi = NotificationCompat.Builder(this, id).apply {
                        setSmallIcon(R.drawable.ic_launcher_background)
                        setContentTitle("TODO")
                        setContentText(text)
                        addAction(R.drawable.ic_launcher_background, "Done", donePendingIntent)
                        setAutoCancel(false)
                    }.build()

                    notifi.flags = Notification.FLAG_NO_CLEAR

                    notificationManager.notify(i, notifi)
                }
            }

            finish()
        })
    }
}
