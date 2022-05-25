package com.example.mydjango

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydjango.databinding.ActivityHinfoBinding

class hInfoViewHolder(val binding : ActivityHinfoBinding): RecyclerView.ViewHolder(binding.root)
class hInfoAdapter(val context: Context, val datas: MutableList<hInfoModel>?)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return hInfoViewHolder(ActivityHinfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as hInfoViewHolder).binding
        val model = datas!![position]
        binding.name.text = model.name
        binding.phone.text = model.phone_number
        binding.addr.text = model.address
    }
}