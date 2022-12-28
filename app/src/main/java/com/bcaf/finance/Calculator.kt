package com.bcaf.finance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_calculator.*
import org.mariuszgromada.math.mxparser.Expression

class Calculator : AppCompatActivity() {

    var lastComma : Boolean = false
    var lastDigit : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
    }

    fun onDigitPress(view: View){
        txtInput.append((view as Button).text)
        lastComma = false
        lastDigit = true
    }

    fun onClear(view: View){
        txtInput.setText("")
    }

    fun onOperatorPress(view: View){
        if(lastDigit && !lastComma) {
            txtInput.append((view as Button).text)
            lastDigit = false
            lastComma = false
        }
    }

    fun onCommaPress(view: View){
        if(lastDigit && !lastComma) {
            txtInput.append(".")
            lastDigit = false
            lastComma = true
        }
    }


    fun onCount(view: View) {
        var count = Expression(txtInput.text.toString())
        txtInput.setText(count.calculate().toString())
    }

    fun onKeluar(v :View){
        finish()
    }


}