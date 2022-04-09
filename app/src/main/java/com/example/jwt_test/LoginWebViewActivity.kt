package com.example.jwt_test

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.*
import androidx.databinding.DataBindingUtil.setContentView
import androidx.security.crypto.MasterKeys
import com.example.jwt_test.databinding.ActivityLoginWebViewBinding
import com.example.jwt_test.utils.Constansts.TAG
import kotlinx.coroutines.*
import java.io.InputStream
import java.net.URL
import java.net.URLConnection

class LoginWebViewActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginWebViewBinding

    private lateinit var masterKeys: String
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "LoginWebViewActivity: onCreate() - called")
        binding = setContentView(this,R.layout.activity_login_web_view)

        binding.lifecycleOwner =this

        //Dispatchers에 대해서 좀 더 공부해볼 것
//        CoroutineScope(Dispatchers.IO).launch {
//            getToken()
//        }
        runBlocking {
            launch {
                getToken()
            }
        }
        //runblocking 말고 다른 스코프로 바꾸기
        //토큰 받으면 웹뷰 닫고 다음 페이지로 전환 구현
        //웹뷰 말고 크롬 커스텀 탭으로 바꾸기 ( WebChromeClient? )
    }

    @SuppressLint("SetJavaScriptEnabled")
    suspend fun getToken() = coroutineScope{
        Log.d(TAG, "LoginWebViewActivity: getToken() - called")
        val intent = intent
        val uri = intent.getStringExtra("URI")

        if (uri != null) {
            var onLoadResource_count = 0

            //웹뷰 설정
            binding.loginWebView.apply {
                binding.loginWebView.webViewClient = object : WebViewClient() {
                    
                    //페이지가 시작될 때 (처음 한 번만 호출되는 메소드)
                    //보통 페이지 로딩이 시작됨을 알린다.
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        Log.d(TAG, "LoginWebViewActivity: onPageStarted() - called")
                        super.onPageStarted(view, url, favicon)
                    }

                    //지정된 url로부터 리소스를 로드할 때 (HTTP로 받아오는 리소스 관리)
                    //리소스가 전부 로드될 때까지 계속해서 호출된다.
                    override fun onLoadResource(view: WebView?, url: String?) {
                        Log.d(TAG, "LoginWebViewActivity: onLoadResource_url = $url")

                        if (url != null) {
                            //임시로 thumb 적어둠
                            if(url.contains("thumb")){
                                
                                //토큰 추출
                                val token = url.substring(6)
                                Log.d(TAG, "LoginWebViewActivity: _token = $token")

                                //SharedPreference에 토큰 저장
                                var pref = this@LoginWebViewActivity.getSharedPreferences("token", Context.MODE_PRIVATE)
                                var editor = pref.edit()
                                editor.putString("token","$token")
                                editor.apply()
                                Log.d(TAG, "LoginWebViewActivity: onLoadResource()_checkPref = ${pref.getString("token","notToken")}")
                                onLoadResource_count++

                                if(onLoadResource_count == 1){
                                    //MainActivity로 이동
                                    val intent = Intent(this@LoginWebViewActivity,MainActivity::class.java)
                                    startActivity(intent)
                                    this@LoginWebViewActivity.finish()
                                }
                            }
                        }
                        super.onLoadResource(view, url)
                    }

                    //방문한 링크를 DB에 업데이트 할 때 호출됨 (방문 URL이 변할 때마다)
                    override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                        Log.d(TAG, "LoginWebViewActivity: doUpdateVisitedHistory_History = ${url}")
                        super.doUpdateVisitedHistory(view, url, isReload)
                    }
                    
                    //페이지 로딩이 완료될 때 호출됨 (onPageStarted와 마찬가지로 처음 한 번만 호출되는 메소드)
                    override fun onPageFinished(view: WebView?, url: String?) {
                        Log.d(TAG, "LoginWebViewActivity: onPageFinished() - called")
                        super.onPageFinished(view, url)
                    }

                    //복구할 수 없는 오류를 호스트 응용 프로그램에게 보고한다.
                    //웹뷰는 인터넷이 연결되어 있지 않았을 때 주소가 노출되는 단점이 있다.
                    //URL주소는 보안상 노출되면 안되기 때문에 숨길경우 사용하면 유용할듯 하다.
                    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                        Log.d(TAG, "LoginWebViewActivity: onReceivedError() - called ")
                        super.onReceivedError(view, request, error)
                    }
                    //onReceivedError가 Http에러가 발생한 경우에 호출되는 메서드
                    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                        Log.d(TAG, "LoginWebViewActivity: onReceivedHttpError() - called ")
                        super.onReceivedHttpError(view, request, errorResponse)
                    }
                    //onReceivedError가 SSL에러가 발생한 경우에 호출되는 메서드
                    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                        Log.d(TAG, "LoginWebViewActivity: onReceivedSslError() - called ")
                        super.onReceivedSslError(view, handler, error)
                    }

                    //인증 요청을 처리할 때 호출됨 (http 인증 요청이 있을 경우)
                    override fun onReceivedHttpAuthRequest(view: WebView?, handler: HttpAuthHandler?, host: String?, realm: String?) {
                        Log.d(TAG, "LoginWebViewActivity: onReceivedHttpAuthRequest() - called")
                        super.onReceivedHttpAuthRequest(view, handler, host, realm)
                    }

                    //확대나 크기등이 변화가 있을 경우 (스케일이 변경되었을 때 처리할 내용을 구현)
                    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
                        Log.d(TAG, "LoginWebViewActivity: onScaleChanged() - called")
                        super.onScaleChanged(view, oldScale, newScale)
                    }

                    //잘못된 키 입력이 있을 경우 호출됨
                    override fun onUnhandledKeyEvent(view: WebView?, event: KeyEvent?) {
                        Log.d(TAG, "LoginWebViewActivity: onUnhandledKeyEvent() - called")
                        super.onUnhandledKeyEvent(view, event)
                    }

                    //사용자의 키 입력이 있을 경우 호출됨 (뒤로가기 버튼 누를 시 전 페이지 , 앞 버튼 누를 시 앞 페이지 구현)
                    override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                        Log.d(TAG, "LoginWebViewActivity: shouldOverrideKeyEvent() - called")
                        return super.shouldOverrideKeyEvent(view, event)
                    }

                    //새로운 url이 현재 WebView에 로드되려고 할 때 호출됨
                    //호스트 응용 프로그램에게 컨트롤을 대신할 기회를 준다.
                    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
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
    //뒤로가기 버튼 누를 시 전 페이지
    override fun onBackPressed() {
        if (binding.loginWebView.canGoBack()) {
            binding.loginWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}