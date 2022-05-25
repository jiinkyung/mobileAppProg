package com.example.ch8_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Toast
import com.example.ch8_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var pauseTime = 0L
    var initTime = 0L // 키 이벤트에서 첫번째 키가 눌린 시각 저장
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener{
            binding.chronometer.base = SystemClock.elapsedRealtime() + pauseTime // chronometer 기준 시간 세팅
            binding.chronometer.start() //chronometer 시작
            binding.btnStart.isEnabled = false // 더 이상 누르지 않게끔 비활성화 시켜줌
            binding.btnStop.isEnabled = true // 다른 버튼은 누를 수 있게 활성화
            binding.btnReset.isEnabled = true
        }
        binding.btnStop.setOnClickListener {
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.btnStart.isEnabled = true
            binding.btnStop.isEnabled = false
            binding.btnReset.isEnabled = true
        }
        binding.btnReset.setOnClickListener {
            pauseTime = 0
            binding.chronometer.base = SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.btnStart.isEnabled = true
            binding.btnStop.isEnabled = true
            binding.btnReset.isEnabled = false
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(System.currentTimeMillis() - initTime > 3000){  //System.currentTimeMillis() : 현재 시간
                Toast.makeText(this, "종료하려면 한번 더 누르세요.", Toast.LENGTH_LONG).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

}