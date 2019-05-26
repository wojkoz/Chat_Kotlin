package com.example.chat_kotlin.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.chat_kotlin.Model.Message
import com.example.chat_kotlin.R

class RecyclerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    var messageList : List<Message> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item ,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.content.text = messageList[position].content
        holder.login.text = messageList[position].login
        holder.date.text = messageList[position].date

    }

    fun setMessageListItems(messageList: List<Message>){
        this.messageList = messageList
        notifyDataSetChanged()
    }
    fun addMessageItem(mes: Message){
        messageList.plusElement(mes)
        notifyDataSetChanged()
    }
    fun updateMessage(message : Message){
        messageList.forEach{
            i -> if(i.id == message.id)  message
        }
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val content: TextView = itemView.findViewById(R.id.user_content)
        val login: TextView = itemView.findViewById(R.id.user_login)
        val date: TextView = itemView.findViewById(R.id.user_date)

    }
}