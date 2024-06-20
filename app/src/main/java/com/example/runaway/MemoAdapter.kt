package com.example.runaway

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.runaway.databinding.ItemMemoBinding

class MyViewHolder(val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root)

class MemoAdapter(val context: Context, val datas: MutableList<Memo>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    lateinit var sharedPreference: SharedPreferences
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

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        val size = sharedPreference.getString("r_size", "15.0f")
        binding.itemMemo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())
    }
}
