package com.bcaf.finance

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_menu.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*

class MenuActivity : AppCompatActivity() {
    var username: String = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val datas: Bundle? = intent.extras
        username = datas?.getString("username", "").toString()
        password = datas?.getString("password", "").toString()

        txtHello.text = "Selamat Datang $username"
        animateText()

        btnPickDate.setOnClickListener(View.OnClickListener { pickDate() })
//        txtUmur.setOnClickListener(View.OnClickListener { pickDate() })

        btnDial.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel: 089505340300")
            }
            startActivity(intent)
        })
    }

    fun animateText() {
        val anim = AlphaAnimation(0.0f, 1f)
        anim.duration = 50
        anim.startOffset = 20
        anim.repeatMode = Animation.REVERSE
        anim.repeatCount = Animation.INFINITE
        txtHello.startAnimation(anim)
    }

    fun pickDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDateSet(
                view: DatePicker, year: Int, monthofYear: Int, dayofMonth: Int
            ) {
                c.set(Calendar.YEAR, year)
                c.set(Calendar.MONTH, monthofYear)
                c.set(Calendar.DAY_OF_MONTH, dayofMonth)

                val different = Period.between(LocalDate.of(year, month, dayofMonth), LocalDate.now())
                txtUmur.setText("${different.years} tahun ${if (different.months >= 12) different.months % 12 else different.months} bulan")

                val myFormat = "dd/MMM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                txtTglLahir.text = sdf.format(c.getTime())
            }
        }

        DatePickerDialog(
            this,
            dateSetListener,
            c.get(Calendar.YEAR),
            c.get(Calendar.MONTH),
            c.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
