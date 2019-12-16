package com.tia.my.app.decl_tweet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException
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

        reEditButton.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, EditTweetActivity::class.java))
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
