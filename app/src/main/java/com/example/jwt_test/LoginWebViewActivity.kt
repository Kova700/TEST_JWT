package com.example.jwt_test

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil.setContentView
import com.example.jwt_test.databinding.ActivityLoginWebViewBinding
import com.example.jwt_test.utils.Constansts.TAG
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class LoginWebViewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "LoginWebViewActivity: onCreate() - called")
        binding = setContentView(this,R.layout.activity_login_web_view)

        binding.lifecycleOwner =this

        //runBlocking 수정 시급
        runBlocking {
            launch {
                getToken()
            }
        }
    }

    //수정 시급
    @SuppressLint("SetJavaScriptEnabled")
    fun getToken(){
        Log.d(TAG, "LoginWebViewActivity: getToken() - called")
        val intent = intent
        val uri = intent.getStringExtra("URI")

        if (uri != null) {
            //웹뷰 설정
            binding.loginWebView.apply {
                binding.loginWebView.webViewClient = object : WebViewClient() {
                    override fun onLoadResource(view: WebView?, url: String?) {
                        Log.d(TAG, "LoginWebViewActivity: onLoadResource_url = $url")
                        if (url != null) {
                            if(url.contains("token:")){
                                val token = url.substring(6)
                                Log.d(TAG, "LoginWebViewActivity: _token = $token")

                                //SharedPreference에 토큰 저장
                                var pref = this@LoginWebViewActivity.getSharedPreferences("token", Context.MODE_PRIVATE)
                                var editor = pref.edit()
                                editor.putString("token","$token")
                                editor.apply()

                                Log.d(TAG, "LoginWebViewActivity: onLoadResource()_checkPref = ${pref.getString("token","notToken")}")
                            }
                        }
                        super.onLoadResource(view, url)
                    }
                    override fun shouldOverrideUrlLoading(view: WebView?,request: WebResourceRequest?): Boolean {
                        Thread{
                            val aURL = URL(request?.url.toString())
                            val conn : URLConnection = aURL.openConnection()
                            conn.connect()
                            val inputstream : InputStream = conn.getInputStream()

                            Log.d(TAG, "LoginWebViewActivity: shouldOverrideUrlLoading_input = ${inputstream.bufferedReader().readLine().toString()}")
                        }.start()
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
                settings.javaScriptEnabled = true
            }

            binding.loginWebView.loadUrl(uri)
        }

    }
}