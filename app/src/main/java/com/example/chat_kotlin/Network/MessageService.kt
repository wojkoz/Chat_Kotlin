package com.example.chat_kotlin.Network

import com.example.chat_kotlin.Model.Message
import com.example.chat_kotlin.Model.SendMessageBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MessageService {
    @GET("messages")
    fun getMessages() : Call<List<Message>>


    @POST("message")
    fun createMessage( @Body m : SendMessageBody ) : Call<Message>

    companion object {

        private const val BaseUrl = "http://tgryl.pl/shoutbox/"

        fun create() : MessageService{
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MessageService::class.java)
        }


    }
}