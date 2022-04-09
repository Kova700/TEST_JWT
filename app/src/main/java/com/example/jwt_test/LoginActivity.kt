package com.example.jwt_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.jwt_test.databinding.ActivityLoginBinding
import com.example.jwt_test.utils.Constansts.TAG
import com.example.jwt_test.utils.LoginType

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "LoginActivity: onCreate() - called")

        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.activity = this

    }
    fun openWiewView(loginType : LoginType){
        Log.d(TAG, "LoginActivity: openWiewView() - called")
        when(loginType){
            LoginType.KAKAO -> {
                Log.d(TAG, "LoginActivity: openWiewView_KAKAO() - called")
                //WebViewActivity로 이동
                val intent = Intent(this,LoginWebViewActivity::class.java)
                intent.putExtra("URI","https://www.daum.net/")
                startActivity(intent)
            }
            LoginType.GOOGLE ->{
                Log.d(TAG, "LoginActivity: openWiewView_GOOGLE() - called")
                //WebViewActivity로 이동
                val intent = Intent(this,LoginWebViewActivity::class.java)
                intent.putExtra("URI","https://www.google.co.kr/")
                startActivity(intent)
            }
        }
    }

}