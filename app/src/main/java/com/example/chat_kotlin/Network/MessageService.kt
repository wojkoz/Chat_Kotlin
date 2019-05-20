package com.example.chat_kotlin.Network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MessageService {
    @GET("messages?")
    fun getMessages() : Call<List<Message>>

    companion object {

        private val BaseUrl = "http://tgryl.pl/shoutbox/"

        fun create() : MessageService{
            val retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MessageService::class.java)
        }


    }
}