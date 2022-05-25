package com.example.myapplication13

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication13.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {
    var datas : MutableList<String>? = null // 전역변수로 선언해줘야함.
    lateinit var adapter : MyAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bgColor = sharedPreferences.getString("color", "")
        binding.rootLayout.setBackgroundColor(Color.parseColor(bgColor))

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            if(controller != null) {
                controller.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
        else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }


        val requestLancher:ActivityResultLauncher<Intent>
        = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val d3 = it.data!!.getStringExtra("result")?.let{
                datas?.add(it) //mutablelist에 값 추가
                adapter.notifyDataSetChanged() // 리사이클러뷰에 추가
            }
            //Log.d("mobileApp", d3!!)
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra("data1", "mobile")
            intent.putExtra("data2", "app")
            //startActivityForResult(intent, 10) //startActivity(intent)
            requestLancher.launch(intent)
        }
        datas = mutableListOf<String>()
        //DB 읽어오기
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from todo_tb", null)
        while(cursor.moveToNext()){
            datas?.add(cursor.getString(1))
        }
        db.close()

        val items = arrayOf<String>("내장")
        binding.fileBtn.setOnClickListener {
            AlertDialog.Builder(this).run {
                setTitle("저장 위치 선택")
                setIcon(android.R.drawable.ic_dialog_info)
                setSingleChoiceItems(items, 1, object:DialogInterface.OnClickListener {
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        if (p1 ==0) { //내장메모리
                            // 저장
                            val file = File(filesDir, "test.txt")
                            val writeStream: OutputStreamWriter = file.writer()
                            writeStream.write("hello android")
                            writeStream.write("$items[p1]")
                            for (i in datas!!.indices)
                                writeStream.write(datas!![i])
                            writeStream.flush()

                            //읽어오기
                            val readStream:BufferedReader = file.reader().buffered()
                            readStream.forEachLine {
                                Log.d("mobileApp", "$it")
                            }
                        }
                    }
                })
                setPositiveButton("선택", null)
                show()
            }
        }
        /*
        datas = savedInstanceState?.let {
            it.getStringArrayList("mydatas")?.toMutableList()
        } ?:let{
            mutableListOf<String>()
        }
*/
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(datas)
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.addItemDecoration( // 투두 항목 구분선
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )

    }
/*
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putStringArrayList("mydatas", ArrayList(datas))
    }
*/
    // addactivity로부터 전달받기
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==10 && resultCode == RESULT_OK) {
            val d3 = data?.getStringExtra("test")
            Log.d("mobileApp", d3!!)
        }
    }*/

    override fun onResume() {
        super.onResume()
        val bgColor = sharedPreferences.getString("color", "")
        binding.rootLayout.setBackgroundColor(Color.parseColor(bgColor))

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_main_setting){
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}