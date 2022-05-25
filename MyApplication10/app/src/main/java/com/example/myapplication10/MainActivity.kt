package com.example.myapplication10

import android.R
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapplication10.databinding.ActivityMainBinding
import com.example.myapplication10.databinding.DialogInputBinding

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 토스트 띄우기_toast
        binding.button1.setOnClickListener {
            // Toast.makeText(this, "첫번째 버튼의 토스트입니다.", Toast.LENGTH_LONG).show() // 토스트 만들기 (show() : 보이기)
            val toast = Toast.makeText(this, "첫번째 버튼의 토스트입니다.", Toast.LENGTH_LONG)
            toast.setText("수정된 토스트입니다.")
            toast.duration = Toast.LENGTH_SHORT
            // API 30 > toast.setGravity(Gravity.TOP, 20, 20) // 토스트 위치 변경: setGravity
            toast.addCallback( // 토스트가 화면에 보이거나 사라지는 순간 콜백으로 감지

                object : Toast.Callback(){
                    override fun onToastHidden() { // 토스트가 사라질 때
                        super.onToastHidden()
                        Log.d("mobileApp", "토스트가 사라집니다.")
                    }

                    override fun onToastShown() { // 토스트가 나타날때때
                       super.onToastShown()
                        Log.d("mobileApp", "토스트가 나타납니다.")
                    }
                }
            )
            toast.show()
        }
        // 데이트 피커_DatePickerDialog
        binding.button2.setOnClickListener {
            DatePickerDialog(this,
                object: DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        // TODO("Not yet implemented")
                        Log.d("mobileApp", "$p1 년, ${p2+1} 월, $p3 일")
                    }
               },
                2022,2,30).show() //month 0~11, 1월이 0 / 날짜: 처음에 보여질 날짜
        }
        // 타임 피커_TimePickerDialog
        binding.button3.setOnClickListener {
            TimePickerDialog(this,
                object : TimePickerDialog.OnTimeSetListener{
                    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
                       // TODO("Not yet implemented")
                        Log.d("mobileApp", "$p1 시 $p2 분")
                    }
                                                           }
                , 13, 0, true).show() // true: 24 false: 12
        }

        val eventHandler = object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                if(p1 == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileApp", "positive button")
                }
                else if (p1 == DialogInterface.BUTTON_NEGATIVE){
                    Log.d("mobileApp", "negative button")
                }
            }
        }
        // 알림창 띄우기_AlertDialog
        binding.button4.setOnClickListener {
            AlertDialog.Builder(this).run{
                setTitle("알림창 테스트")
                setIcon(R.drawable.ic_dialog_info)
                setMessage("정말 종료하시겠습니까?")
                setPositiveButton("YES", eventHandler)
                setNegativeButton("NO", eventHandler)
                setNeutralButton("MORE", null)
                setCancelable(false) // true 주면 뒤로가기 버튼 누르면 창이 사라짐. 이 속성 안주면 default로 true 속성 적용됨.
                show()
            }.setCanceledOnTouchOutside(false) // 창 바깥 클릭했을 때 창이 사라지도록함.
        }

        val items = arrayOf<String>("사과", "딸기", "복숭아", "토마토") // 알림창에 제공할 목록들

        binding.button5.setOnClickListener { // 대화상자2
            AlertDialog.Builder(this).run{
                setTitle("아이템 목록 선택")
                setIcon(R.drawable.ic_dialog_info)
                setItems(items, object:DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Log.d("mobileApp", "${items[p1]}")
                    }
                })
                setPositiveButton("닫기", null)
                setCancelable(false)
                show()

            }
        }

        // 체크박스_다중 선택 가능 setMultiChoiceItems 대화상자3
        binding.button6.setOnClickListener {
            AlertDialog.Builder(this).run{
                setTitle("멀티 아이템 목록 선택")
                setIcon(R.drawable.ic_dialog_info)
                setMultiChoiceItems(items, booleanArrayOf(false, true, false, false), // 초기값 설정
                object : DialogInterface.OnMultiChoiceClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int, p2: Boolean) { //p2: 선택 여부 true, false 표현
                        Log.d("mobileApp", "${items[p1]} ${if(p2) "선택" else "해제"}")
                    }
                })

                setPositiveButton("닫기", null)
                show()
            }.setCanceledOnTouchOutside(false)
        }

        // 라디오 버튼_싱글 아이템 선택 setSingleChoiceItems 대화상자4
        binding.button7.setOnClickListener {
            AlertDialog.Builder(this).run{
                setTitle("싱글 아이템 목록 선택")
                setIcon(R.drawable.ic_dialog_info)
                setSingleChoiceItems(items, 1, object:DialogInterface.OnClickListener{ //checkedItem: 초기값
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        Log.d("mobileApp", "${items[p1]}")
                    }
                })

                setPositiveButton("닫기", null)
                show()
            }
        }

        // 커스텀 다이얼로그
        val dialogBinding = DialogInputBinding.inflate(layoutInflater)
        val alert = AlertDialog.Builder(this)
            .setTitle("입력")
            .setView(dialogBinding.root) // 커스텀 다이얼로그 출력
            .setPositiveButton("닫기", null)
            .create()

        binding.button8.setOnClickListener {
           alert.show()
        }

    }
}