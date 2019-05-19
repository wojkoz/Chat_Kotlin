package com.example.chat_kotlin.Network

import com.google.gson.annotations.SerializedName

class Message {
    @SerializedName("content")
    var content : String? = null
    @SerializedName("login")
    var login : String? = null
    @SerializedName("date")
    var date : String? = null
    @SerializedName("id")
    var id : String? = null
}