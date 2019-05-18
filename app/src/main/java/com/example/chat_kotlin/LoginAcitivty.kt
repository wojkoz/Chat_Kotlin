package com.example.chat_kotlin

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_first_login_acitivty.*

class LoginAcitivty : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_login_acitivty)
        set_login.setOnClickListener {
                setSharedPrefLogin()
            }
    }


    private fun setSharedPrefLogin(){
        val sharedPref = getSharedPreferences(R.string.FILE_KEY.toString(), Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString(R.string.LOGIN_KEY.toString(), login_input.text.toString())
            apply()
        }
        finish()

    }
}
