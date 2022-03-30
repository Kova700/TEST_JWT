package com.example.jwt_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.jwt_test.databinding.ActivityMainBinding
import com.example.jwt_test.utils.Constansts.TAG
import com.example.jwt_test.utils.LoginType

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity: onCreate() - called")

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.lifecycleOwner = this

    }
    fun openWiewView(loginType : LoginType){
        Log.d(TAG, "MainActivity: openWiewView() - called")
        when(loginType){
            LoginType.KAKAO -> {
                Log.d(TAG, "MainActivity: openWiewView_KAKAO() - called")
                //WebViewActivity로 이동
                val intent = Intent(this,LoginWebViewActivity::class.java)
                intent.putExtra("URI","http://172.30.1.14:8080/oauth2/authorize/kakao")
                startActivity(intent)
            }
            LoginType.GOOGLE ->{
                Log.d(TAG, "MainActivity_GOOGLE() - called")
                //WebViewActivity로 이동
                val intent = Intent(this,LoginWebViewActivity::class.java)
                intent.putExtra("URI","http://10.0.2.2:8080/oauth2/authorize/google")
                startActivity(intent)
            }
        }
    }

}