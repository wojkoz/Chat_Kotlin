package com.example.chat_kotlin.Network

import retrofit2.Call
import retrofit2.http.GET

interface MessageService {
    @GET("messages")
    fun getMessages() : Call<MessageResponse>
}