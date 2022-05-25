package com.example.myapplication8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 터치 이벤트
    override fun onTouchEvent(event: MotionEvent?): Boolean { // ?: null 허용
        when(event?.action){ //event 클래스 안에 action 이라는 멤버
            MotionEvent.ACTION_DOWN -> {
                Log.d("mobileApp", "Action DOWN : ${event.x}, ${event.y}")
            }
            MotionEvent.ACTION_UP -> {
                Log.d("mobileApp", "Action UP : ${event.rawX}, ${event.rawY}")
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d("mobileApp", "Action MOVE")
            }
        }
        return super.onTouchEvent(event)
    }


    //키 이벤트
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("mobileApp", "Key Down")
        when(keyCode){
            KeyEvent.KEYCODE_0 -> {Log.d("mobileApp", "0 Key Down")} // 0이 눌렸을 때
            KeyEvent.KEYCODE_B -> {Log.d("mobileApp", "B Key Down")} // B가 눌렸을 때
            //KeyEvent.KEYCODE_BACK -> {Log.d("mobileApp", "뒤로가기 Key Down")}
            KeyEvent.KEYCODE_VOLUME_UP -> {Log.d("mobileApp", "VOLUME_UP")}
            KeyEvent.KEYCODE_VOLUME_DOWN -> {Log.d("mobileApp", "VOLUME_DOWN")}
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d("mobileApp", "Key Up")
        return super.onKeyUp(keyCode, event)
    }

    override fun onBackPressed() {
        Log.d("mobileApp", "뒤로가기 Key Down")
    }
}