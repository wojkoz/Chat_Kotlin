package com.example.chat_kotlin.model


import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("login")
    val login: String
)