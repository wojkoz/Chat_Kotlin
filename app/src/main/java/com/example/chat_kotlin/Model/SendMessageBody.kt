package com.example.chat_kotlin.Model


import com.google.gson.annotations.SerializedName

data class SendMessageBody(
    @SerializedName("content")
    val content: String,
    @SerializedName("login")
    val login: String
)