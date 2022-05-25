package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var button1 : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        setContentView(R.layout.activity_main)

        val tv1 : TextView =findViewById(R.id.textView1)
        val rbar : RatingBar = findViewById(R.id.ratingBar)
        val btn : Button = findViewById(R.id.button)
        val chb : CheckBox = findViewById(R.id.checkBox)
        val rdo : RadioButton = findViewById(R.id.radioButton3)
        // val tv1 = findViewById<TextView>(R.id.textView1)


        tv1.visibility = View.INVISIBLE
        btn.visibility = View.INVISIBLE
        */

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.textView1.visibility = View.INVISIBLE
        binding.button.visibility = View.INVISIBLE

        Log.d("myCheck","안드로이드 시작 - 로그 출력")
    }
}