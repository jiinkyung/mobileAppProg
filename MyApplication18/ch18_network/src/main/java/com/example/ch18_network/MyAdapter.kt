package com.example.ch18_network

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ch18_network.databinding.ItemMainBinding


class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)
class MyAdapter(val context : Context, val datas : MutableList<ItemModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        val model = datas!![position]
        binding.name.text = model.orgnm
        binding.tel.text = model.orgTlno
        binding.addr.text = model.orgZipaddr
    }

}