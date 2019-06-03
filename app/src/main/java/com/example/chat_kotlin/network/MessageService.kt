package com.example.chat_kotlin.network

import com.example.chat_kotlin.model.DeletedMessage
import com.example.chat_kotlin.model.Message
import com.example.chat_kotlin.model.SendMessageBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MessageService {
    @GET("messages")
    fun getMessages() : Call<MutableList<Message>>


    @POST("message")
    fun createMessage( @Body m : SendMessageBody ) : Call<Message>

    @PUT("message/{id}")
    fun updateMessage(@Path("id") id : String, @Body m :SendMessageBody) : Call<Message>

    @DELETE("message/{id}")
    fun deleteMessage(@Path("id") id : String) : Call<DeletedMessage>

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