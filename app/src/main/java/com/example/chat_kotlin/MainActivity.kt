package com.example.chat_kotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.chat_kotlin.Acitivites.LoginAcitivty
import com.example.chat_kotlin.Model.Message
import com.example.chat_kotlin.Model.SendMessageBody
import com.example.chat_kotlin.Network.MessageService
import com.example.chat_kotlin.recycler.RecyclerAdapter
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var userLogin: String? = ""
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        val sharedPref = getSharedPreferences(R.string.FILE_KEY.toString(), Context.MODE_PRIVATE) ?: return
        userLogin = sharedPref.getString(R.string.LOGIN_KEY.toString(), null )

        if(userLogin==null){
            val intent = Intent(this, LoginAcitivty::class.java)
            startActivity(intent)
        }

        recyclerView = recyclerview
        recyclerAdapter = RecyclerAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

        getNewMessages()

        //send button listener
        send_button.setOnClickListener{
            sendNewMessage(input_message.text.toString())
            input_message.text.clear()
            input_message.clearFocus()
            it.hideKeyboard()
        }

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun sendNewMessage(m: String ){
            MessageService.create().createMessage(SendMessageBody(m, userLogin!!))
                .enqueue(object : Callback<Message>{
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        recyclerAdapter.addMessageItem(response.body()!!)
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                })

    }

    override fun onResume() {
        super.onResume()
        val sharedPref = getSharedPreferences(R.string.FILE_KEY.toString(), Context.MODE_PRIVATE) ?: return
        userLogin = sharedPref.getString(R.string.LOGIN_KEY.toString(), null )
        getNewMessages()
    }

    private fun getNewMessages(){

        val service = MessageService.create()
        val call = service.getMessages()


        call.enqueue(object : Callback<List<Message>>{
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                if(response.code() == 200){
                    recyclerAdapter.setMessageListItems(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                    t.printStackTrace()
            }
        })
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
