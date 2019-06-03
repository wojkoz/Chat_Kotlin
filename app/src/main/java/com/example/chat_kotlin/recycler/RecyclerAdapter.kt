package com.example.chat_kotlin.recycler

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chat_kotlin.model.Message
import com.example.chat_kotlin.R
import kotlinx.android.synthetic.main.recyclerview_item.view.*



class RecyclerAdapter(private val clickListener: (Message) -> Unit) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    private var messageList : MutableList<Message> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item ,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(messageList[position], clickListener)
    }

    fun setMessageListItems(messageList: MutableList<Message>){
        this.messageList = messageList
        notifyDataSetChanged()
    }




    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Message, clickListener: (Message) -> Unit) {
            itemView.user_content.text = item.content
            itemView.user_login.text = item.login
            itemView.user_date.text = item.date
            itemView.setOnClickListener { clickListener(item)}
        }
    }
}