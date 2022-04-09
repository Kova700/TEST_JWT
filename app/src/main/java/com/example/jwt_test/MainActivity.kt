package com.example.jwt_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.jwt_test.adapter.MainChatRoomAdapter
import com.example.jwt_test.databinding.ActivityMainBinding
import com.example.jwt_test.utils.Constansts.TAG
import com.example.jwt_test.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var chatRoomAdapter: MainChatRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        with(binding){
            lifecycleOwner =this@MainActivity
            model = mainViewModel
            activity = this@MainActivity

            //프로필 이미지 라운드 설정
            binding.profile.clipToOutline = true

            chatRoomAdapter = MainChatRoomAdapter()
            chatRoomRecyclerView.adapter = chatRoomAdapter
            chatRoomRecyclerView.setHasFixedSize(true)
            chatRoomRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(chatRoomRecyclerView)
        }

    }
    fun open_closeMenu() {
        with(binding){
            if(drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                Log.d(TAG, "MainActivity: closeMenu() - called")
                drawerlayout.closeDrawer(Gravity.RIGHT)
            } else {
                Log.d(TAG, "MainActivity: openMenu() - called")
                drawerlayout.openDrawer(Gravity.RIGHT)
            }
        }
    }
    fun goBookShelf() {
        val intent = Intent(this,BookShelfActivity::class.java)
        startActivity(intent)
    }
    fun goSearch(){
        val intent = Intent(this,SearchActivity::class.java)
        startActivity(intent)
    }

}