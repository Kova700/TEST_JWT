package com.example.jwt_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.jwt_test.R
import com.example.jwt_test.databinding.ItemBookReadBinding

class MainReadBookAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var itemBookReadBinding : ItemBookReadBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        itemBookReadBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_book_read,parent,false)
        return ReadBookViewHolder(itemBookReadBinding)
    }


    override fun getItemCount(): Int {
        return  10
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ReadBookViewHolder){
//            holder.binding.bookName.text = "1234"
        }
    }

    //뷰 홀더의 파라미터로 바인딩 클래스를 받는다.
    inner class ReadBookViewHolder(val binding: ItemBookReadBinding)
        : RecyclerView.ViewHolder(binding.root){
    }
}