package com.bcaf.finance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var container :LinearLayout
    val defaultPassword = "12345"
    val defaultUsername = "User"
    var counter = 0

    fun init(){

        container = findViewById(R.id.containerDummy)
        btnLogin.setOnClickListener(View.OnClickListener{
//            var button = Button(applicationContext)
//            button.text = "Button" + counter++
//            container.addView(button )
            checkPassword(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun checkPassword(v :View){
        if(txtUsername.text.contentEquals(defaultUsername) && txtPassword.text.contentEquals(defaultPassword) ){
            Toast.makeText(applicationContext, "Berhasil login", Toast.LENGTH_LONG).show()
            val intent = Intent(this,MainPortofolio::class.java)
            intent.putExtra("username",txtUsername.text.toString())
            intent.putExtra("password",txtPassword.text.toString())
            startActivity(intent)
        }else{
            Toast.makeText(applicationContext, "Username/Password tidak terdaftar", Toast.LENGTH_LONG).show()
        }
    }

    fun ForgotLink(v :View){
//        Toast.makeText(applicationContext, "Forgot Password", Toast.LENGTH_LONG).show()
        finish()
    }


    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle Activity", "Start action")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle Activity","Resume action")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle Activity","Pause action")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle Activity","Stop action")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle Activity","Destroy action")
    }
}