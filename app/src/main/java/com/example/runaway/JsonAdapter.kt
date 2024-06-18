package com.example.runaway

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.runaway.databinding.ItemMainBinding

class JsonViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)
class JsonAdapter (val datas:MutableList<MyJsonItems>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JsonViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as JsonViewHolder).binding
        val model = datas!![position]

        Log.d("RecyclerView", "Binding data: ${model.shel_nm}, ${model.address}")


        binding.itemName.text = model.shel_nm
        binding.itemAddr.text = model.address
        //binding.itemDistance.text = model.issueGbn
        binding.root.setOnClickListener {
            if (binding.shelterDetail.visibility == View.VISIBLE) {
                // 현재 보이는 상태라면 숨김 처리
                binding.shelterDetail.visibility = View.GONE
            } else {
                // 현재 숨겨진 상태라면 보이도록 처리
                binding.shelterDetail.visibility = View.VISIBLE
            }
        }
        binding.btnStar.setOnClickListener {
            if (binding.btnSave.visibility == View.VISIBLE) {
                // 현재 보이는 상태라면 숨김 처리
                binding.btnSave.visibility = View.GONE
                binding.btnSave1.visibility = View.VISIBLE
            } else if(binding.btnSave1.visibility == View.VISIBLE){
                // 현재 숨겨진 상태라면 보이도록 처리
                binding.btnSave.visibility = View.VISIBLE
                binding.btnSave1.visibility = View.GONE
            }
        }
    }
}