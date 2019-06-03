package com.example.chat_kotlin.activity


import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chat_kotlin.MainActivity
import com.example.chat_kotlin.R
import kotlinx.android.synthetic.main.activity_edit_message_acitivty.*

class EditMessageAcitivty : AppCompatActivity() {
    private var userLogin: String = ""
    private var login: String = ""
    private var id: String = ""
    private var content: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_message_acitivty)

        login = intent.getStringExtra("login")
        edit_login.setText(login)
        content = intent.getStringExtra("content")
        edit_content.setText(content)
        userLogin = intent.getStringExtra("user_login")
        id = intent.getStringExtra("id")

        update_button.setOnClickListener{
            checkUpdate()
        }

        delete_button.setOnClickListener{
            checkDelete()
        }
    }

    private fun checkUpdate(){
        if (edit_login.text.toString() != ""  && edit_content.text.toString() != ""){
            val res = Intent()
            res.putExtra(MainActivity.DATA_TYPE, MainActivity.DATA_UPDATE)
            res.putExtra(MainActivity.DATA_USER_LOGIN, edit_login.text.toString())
            res.putExtra(MainActivity.DATA_USER_CONTENT, edit_content.text.toString())
            res.putExtra(MainActivity.DATA_USER_ID, id)

            setResult(Activity.RESULT_OK, res)
            finish()
        }
    }

    private fun checkDelete(){
        if(userLogin == login){
            val res = Intent()
            res.putExtra(MainActivity.DATA_TYPE, MainActivity.DATA_DELETE)
            res.putExtra(MainActivity.DATA_USER_ID, id)

            setResult(Activity.RESULT_OK, res)
            finish()
        }
    }

}
