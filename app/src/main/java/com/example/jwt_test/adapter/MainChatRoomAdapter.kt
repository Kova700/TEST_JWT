package com.example.jwt_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jwt_test.R
import com.example.jwt_test.databinding.ItemMainChatroomBinding

class MainChatRoomAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var itemMainChatroomBinding: ItemMainChatroomBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemMainChatroomBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_main_chatroom,parent,false)
        return ChatRoomViewHolder(itemMainChatroomBinding)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ChatRoomViewHolder){
//            holder.binding.bookName.text = "1234"
        }
    }

    inner class ChatRoomViewHolder(val binding: ItemMainChatroomBinding)
        :RecyclerView.ViewHolder(binding.root){
        }
}