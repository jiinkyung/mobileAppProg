package com.example.myapplication13

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.example.myapplication13.databinding.ActivityAddBinding


class AddActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val d1 = intent.getStringExtra("data1")
        val d2 = intent.getStringExtra("data2")

        //binding.tv.text = (d1+d2)

        /*binding.button1.setOnClickListener { // addactivity 에서 mainactivit로 다시 돌아가기-전달
            intent.putExtra("test", "world")
            setResult(RESULT_OK, intent)
            finish()
        }*/
        binding.button2.setOnClickListener {
            val intent = Intent()
            intent.action = "ACTION_EDIT"
            intent.data = Uri.parse("http://www.google.com")
            startActivity(intent)
        }

        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.button3.setOnClickListener {
            binding.addEditView.requestFocus()
            manager.showSoftInput(binding.addEditView, InputMethodManager.SHOW_IMPLICIT)
        }
        binding.button4.setOnClickListener {
            manager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
        binding.button5.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    // 투두 내용 저장
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add_save) {
            val inputData = binding.addEditView.text.toString()
            // DB에 저장하기
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into todo_tb (todo) values (?)", arrayOf<String>(inputData))
            db.close()

            intent.putExtra("result", inputData )
            setResult(RESULT_OK, intent)
            finish()
            return true
        }
        return false
    }
}