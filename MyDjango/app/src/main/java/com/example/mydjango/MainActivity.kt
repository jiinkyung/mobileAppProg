package com.example.mydjango

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydjango.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDjango.setOnClickListener {
            getList()
        }

        binding.btnPost.setOnClickListener {
            binding.layout1.visibility = View.VISIBLE
            binding.layout2.visibility = View.VISIBLE
            binding.layout3.visibility = View.VISIBLE
            binding.btnPost2.visibility = View.VISIBLE
        }
        binding.btnPost2.setOnClickListener {
            val name = binding.edtName.text.toString()
            val phone = binding.edtPhone.text.toString()
            val addr = binding.edtAddr.text.toString()
            if(name!="" && phone!="" && addr!=""){
                val data = hInfoModel(name, phone, addr)
                var call:Call<hInfoModel> = MyApplication.apiService.postData(data)
                call?.enqueue(object:Callback<hInfoModel>{
                    override fun onResponse(
                        call: Call<hInfoModel>,
                        response: Response<hInfoModel>
                    ) {
                        getList()
                    }

                    override fun onFailure(call: Call<hInfoModel>, t: Throwable) {
                        Log.d("mobileApp", "error.......")
                    }
                })
            }
            else {
                Toast.makeText(this, "데이터를 모두 입력하세요", Toast.LENGTH_SHORT).show()
            }
            binding.edtName.text.clear()
            binding.edtPhone.text.clear()
            binding.edtAddr.text.clear()

            binding.layout1.visibility = View.GONE
            binding.layout2.visibility = View.GONE
            binding.layout3.visibility = View.GONE
            binding.btnPost2.visibility = View.GONE
        }
    }
    private fun getList(){
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