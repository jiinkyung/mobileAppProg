package com.example.ch18_image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_image.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            var call : Call<PageListModel> = MyApplication.networkService.getList(
                1,
                10,
                "json",
                "4dyWzA6P86MqeODeWSta/GWsVhhhGnt66S3voAAXhcnXgldM3gqVOgur7RO6yDvALtfM+0RfkoMt52QPQbmTCw=="
            )
            call?.enqueue(object: Callback<PageListModel> {
                override fun onResponse(
                    call: Call<PageListModel>,
                    response: Response<PageListModel>
                ) {
                    if(response.isSuccessful){
                        Log.d("mobileApp", "${response.body()}")
                        binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        binding.retrofitRecyclerView.adapter = MyAdapter(this@MainActivity, response.body()?.data)
                    }
                }

                override fun onFailure(call: Call<PageListModel>, t: Throwable) {
                    Log.d("mobileApp", "onFailure...")
                }
            })
        }
    }
}