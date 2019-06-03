package com.example.chat_kotlin.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chat_kotlin.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
