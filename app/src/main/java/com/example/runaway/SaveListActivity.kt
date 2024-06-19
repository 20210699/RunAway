package com.example.runaway

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runaway.databinding.ActivitySaveListBinding

class SaveListActivity : AppCompatActivity() {
    lateinit var binding: ActivitySaveListBinding
    var itemList = mutableListOf<ShelterData>()
    var adapter: MySaveAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //툴바 뒤로가기 활성화
        var toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 뒤로가기 버튼 활성화
        supportActionBar?.title = "저장한 대피소"

        MyApplication.db.collection("place")
            //정렬 안 함
            .get()
            .addOnSuccessListener { result ->
                itemList = mutableListOf<ShelterData>()
                for (document in result) {
                    val item = document.toObject(ShelterData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                binding.saveRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.saveRecyclerView.addItemDecoration(
                    DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
                )
                binding.saveRecyclerView.adapter = MySaveAdapter(this, itemList)

            }
            .addOnFailureListener {
                Log.d("데이터 불러오기", "실패")
            }
    }

    override fun onStart(){
        super.onStart()
        refreshAdapter()
    }
    fun refreshAdapter() {
        MyApplication.db.collection("place")
            //정렬 안 함
            .get()
            .addOnSuccessListener { result ->
                itemList = mutableListOf<ShelterData>()
                for (document in result) {
                    val item = document.toObject(ShelterData::class.java)
                    item.docId = document.id
                    itemList.add(item)
                }
                binding.saveRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.saveRecyclerView.adapter = MySaveAdapter(this, itemList)

            }
            .addOnFailureListener {
                Log.d("데이터 불러오기", "실패")
            }

        MySaveAdapter(this, itemList)
        adapter?.notifyDataSetChanged()
    }

    //뒤로가기
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // 뒤로가기 버튼 눌렀을 때
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}