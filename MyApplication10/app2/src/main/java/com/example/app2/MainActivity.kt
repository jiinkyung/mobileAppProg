package com.example.app2

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.app2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            val notification : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) // 소리 식별값 얻기_Uri 객체
            val rington = RingtoneManager.getRingtone(applicationContext, notification) // 소리를 재생하는 rington 객체
            rington.play()
        }
        binding.button2.setOnClickListener {
            val player : MediaPlayer = MediaPlayer.create(this, R.raw.funny_voices) // 사용자가 가져온 음원 사용하려면 mediaplayer 사용해여함
            player.start()
        }

        //AndroidManifest 파일에 permission 선언해야함
        binding.button3.setOnClickListener {
            // Vibrator 객체 획득 (버전에 따라)
            val vibrator = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){ // 현재 버전과 비교
                val vibratorManager = this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            }
            else {
                getSystemService(VIBRATOR_SERVICE) as Vibrator // 예전버전
            }

            // Vibrator를 이용한 진동 (버전에 따라)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE))
            }
            else {
                vibrator.vibrate(500) // 진동 세기
            }

        }
    }
}