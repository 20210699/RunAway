package com.example.runaway

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.runaway.databinding.ItemMemoBinding

class MyViewHolder(val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)

class MemoAdapter(val datas: MutableList<Memo>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int {
        return datas?.size ?:0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        MyViewHolder(ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent,false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        val memo = datas!![position]
        binding.itemDate.text = memo.date
        binding.itemMemo.text = memo.memo
    }
}
