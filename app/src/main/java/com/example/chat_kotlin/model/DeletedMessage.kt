package com.example.chat_kotlin.model


import com.google.gson.annotations.SerializedName

data class DeletedMessage(
    @SerializedName("content")
    val content: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("_id")
    val id: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("__v")
    val v: Int
)