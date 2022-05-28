package com.example.mydjango

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydjango.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDjango.setOnClickListener {
            var call : Call<MutableList<hInfoModel>> = MyApplication.apiService.getList("json")
            call?.enqueue(object : Callback<MutableList<hInfoModel>>{
                override fun onResponse(
                    call: Call<MutableList<hInfoModel>>,
                    response: Response<MutableList<hInfoModel>>
                ) {
                    if(response.isSuccessful){
                        binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.recyclerView.adapter = hInfoAdapter(this@MainActivity, response.body()?.toMutableList<hInfoModel>())
                    }
                }

                override fun onFailure(call: Call<MutableList<hInfoModel>>, t: Throwable) {
                    Log.d("mobileApp", "error.......")
                }
            })
        }
    }
}