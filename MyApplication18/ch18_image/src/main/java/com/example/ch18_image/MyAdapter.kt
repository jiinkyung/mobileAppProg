package com.example.ch18_image

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ch18_image.databinding.ItemMainBinding

class MyViewHolder(val binding: ItemMainBinding):RecyclerView.ViewHolder(binding.root)
class MyAdapter (val context: Context, val datas: MutableList<ItemModel>?):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas?.size?:0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        val model = datas!![position]
        binding.country.text = model.country_nm
        Glide.with(binding.root)
            .load(model.download_url)
            .override(400, 300)
            .into(binding.flag)
    }
}