package com.example.jwt_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.jwt_test.adapter.MainReadBookAdapter
import com.example.jwt_test.adapter.MainReadingBookAdapter
import com.example.jwt_test.databinding.ActivityBookShelfBinding

class BookShelfActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBookShelfBinding
    private lateinit var readingBookAdapter: MainReadingBookAdapter
    private lateinit var readBookAdapter: MainReadBookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_shelf)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_book_shelf)

        with(binding){
            lifecycleOwner =this@BookShelfActivity
            activity = this@BookShelfActivity

            readingBookAdapter = MainReadingBookAdapter()
            readingBookRecyclerView.adapter = readingBookAdapter
            readingBookRecyclerView.setHasFixedSize(true)
            readingBookRecyclerView.layoutManager = LinearLayoutManager(this@BookShelfActivity,
                RecyclerView.HORIZONTAL,false)
            val snapHelper1 = LinearSnapHelper()
            snapHelper1.attachToRecyclerView(readingBookRecyclerView)

            readBookAdapter = MainReadBookAdapter()
            readBookRecyclerView.adapter = readBookAdapter
            readBookRecyclerView.setHasFixedSize(true)
            readBookRecyclerView.layoutManager = LinearLayoutManager(this@BookShelfActivity,
                RecyclerView.HORIZONTAL,false)
            val snapHelper2 = LinearSnapHelper()
            snapHelper2.attachToRecyclerView(readBookRecyclerView)
        }
    }

    fun goMain(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    fun goSearch(){
        val intent = Intent(this,SearchActivity::class.java)
        startActivity(intent)
    }
}