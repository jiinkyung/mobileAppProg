package com.example.myapplication20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication20.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility
import okhttp3.internal.Util

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val keyHash = Utility.getKeyHash(this)
        Log.d("mobileApp", keyHash)

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            if(binding.btnLogin.text.equals("로그인"))
                intent.putExtra("data", "logout")
            else if(binding.btnLogin.text.equals("로그아웃"))
                intent.putExtra("data", "login")
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if(MyApplication.checkAuth() || MyApplication.email != null){
            binding.btnLogin.text = "로그아웃"
            binding.authTv.text = "${MyApplication.email}님 반갑습니다."
            binding.authTv.textSize = 16F
        }
        else {
            binding.btnLogin.text = "로그인"
            binding.authTv.text = "덕성 모바일"
            binding.authTv.textSize = 24F
        }
    }
}