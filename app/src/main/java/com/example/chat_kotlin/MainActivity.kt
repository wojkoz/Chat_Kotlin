package com.example.chat_kotlin
//Wojciech Koziol
import android.app.Activity
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
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.chat_kotlin.activity.EditMessageAcitivty
import com.example.chat_kotlin.activity.LoginActivity
import com.example.chat_kotlin.model.DeletedMessage
import com.example.chat_kotlin.model.Message
import com.example.chat_kotlin.model.SendMessageBody
import com.example.chat_kotlin.network.MessageService
import com.example.chat_kotlin.recycler.RecyclerAdapter
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var userLogin: String? = ""
    private lateinit var recyclerView: RecyclerView
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
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        recyclerView = recyclerview
        recyclerAdapter = RecyclerAdapter { item -> itemClicked(item)}
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
    companion object{
        const val UPDATE_DELETE_MESSAGE_REQ_CODE: Int = 1
        const val DATA_TYPE = "TYPE"
        const val DATA_UPDATE = "UPDATE"
        const val DATA_DELETE = "DELETE"
        const val DATA_USER_LOGIN = "l"
        const val DATA_USER_CONTENT = "c"
        const val DATA_USER_ID = "id"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == UPDATE_DELETE_MESSAGE_REQ_CODE){
            if(resultCode == Activity.RESULT_OK){

                if(data?.getStringExtra(DATA_TYPE) == DATA_UPDATE){

                    val login = data.getStringExtra(DATA_USER_LOGIN)
                    val content = data.getStringExtra(DATA_USER_CONTENT)
                    val id = data.getStringExtra(DATA_USER_ID)

                    updateOldMessage(id, SendMessageBody(content, login))

                }else if(data?.getStringExtra(DATA_TYPE) == DATA_DELETE){
                    val id = data.getStringExtra(DATA_USER_ID)
                    deleteMessage(id!!)
                }
            }
        }
    }

    private fun itemClicked(Item : Message) {
        val intent = Intent(this, EditMessageAcitivty::class.java)
        intent.putExtra("login", Item.login)
        intent.putExtra("content", Item.content)
        intent.putExtra("id", Item.id)
        intent.putExtra("user_login", userLogin)
        startActivityForResult(intent, UPDATE_DELETE_MESSAGE_REQ_CODE)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun sendNewMessage(m: String ){
            MessageService.create().createMessage(SendMessageBody(m, userLogin!!))
                .enqueue(object : Callback<Message>{
                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        getNewMessages()
                    }

                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                })
    }


    private fun deleteMessage(i: String ){
            MessageService.create().deleteMessage(i)
                .enqueue(object : Callback<DeletedMessage>{
                    override fun onFailure(call: Call<DeletedMessage>, t: Throwable) {
                        Log.w("Delete",t.message)
                    }

                    override fun onResponse(call: Call<DeletedMessage>, response: Response<DeletedMessage>) {
                        getNewMessages()
                    }
                })
        }

    private fun updateOldMessage(i: String, body: SendMessageBody){
            MessageService.create().updateMessage(i, body)
                .enqueue(object : Callback<Message>{
                    override fun onFailure(call: Call<Message>, t: Throwable) {
                        Log.w("update",t.message)
                        Toast.makeText(this@MainActivity, "update Fail", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Message>, response: Response<Message>) {
                        getNewMessages()
                        Toast.makeText(this@MainActivity, "update DONE", Toast.LENGTH_LONG).show()
                    }

                })
        }
    fun getNewMessages(){

            val service = MessageService.create()
            val call = service.getMessages()


            call.enqueue(object : Callback<MutableList<Message>>{
                override fun onResponse(call: Call<MutableList<Message>>, response: Response<MutableList<Message>>) {
                    if(response.code() == 200){
                        recyclerAdapter.setMessageListItems(response.body()!!)
                    }
                }

                override fun onFailure(call: Call<MutableList<Message>>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }


    override fun onResume() {
        super.onResume()
        val sharedPref = getSharedPreferences(R.string.FILE_KEY.toString(), Context.MODE_PRIVATE) ?: return
        userLogin = sharedPref.getString(R.string.LOGIN_KEY.toString(), null )


        getNewMessages()

    }



    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {

            }
            R.id.settings -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
