package com.example.runaway

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.runaway.databinding.ItemSaveBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MySaveViewHolder(val binding: ItemSaveBinding) : RecyclerView.ViewHolder(binding.root)
class MySaveAdapter(val context: Context, val itemList: MutableList<ShelterData>):RecyclerView.Adapter<MySaveViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySaveViewHolder {
        return MySaveViewHolder(ItemSaveBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MySaveViewHolder, position: Int) {
        val data = itemList[position]
        val user = Firebase.auth.currentUser

        holder.binding.run {
            if (user?.email == data.email) {
                itemName.text = data.pname
                itemAddr.text = data.paddress
            }
        }
    }

}