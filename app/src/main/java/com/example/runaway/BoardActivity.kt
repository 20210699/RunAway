package com.example.runaway

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runaway.databinding.ActivityBoardBinding
import com.google.firebase.firestore.Query

class BoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityBoardBinding
    lateinit var adapter: BoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainFab.setOnClickListener {
            if (MyApplication.checkAuth()) {
                startActivity(Intent(this, AddActivity::class.java))
            } else {
                Toast.makeText(this, "인증을 먼저 진행해주세요..", Toast.LENGTH_LONG).show()
            }
        }
    }
    override fun onStart(){
        super.onStart()

        if(MyApplication.checkAuth()){
            MyApplication.db.collection("comments")
                .orderBy("date_time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {result ->
                    val itemList = mutableListOf<ItemData>()
                    for(document in result){
                        val item = document.toObject(ItemData::class.java)
                        item.docId = document.id
                        itemList.add(item)
                    }
                    adapter = BoardAdapter(this, itemList)
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.recyclerView.addItemDecoration(
                            DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
                            )
                    binding.recyclerView.adapter = adapter

                }
                .addOnFailureListener {
                    Toast.makeText(this, "서버 데이터 획득 실패", Toast.LENGTH_LONG).show()
                }
        }
    }

}