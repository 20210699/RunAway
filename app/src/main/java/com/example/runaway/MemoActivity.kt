package com.example.runaway

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runaway.databinding.ActivityMemoBinding
import java.text.SimpleDateFormat

class MemoActivity : AppCompatActivity() {
    lateinit var binding :ActivityMemoBinding
    lateinit var datas: MutableList<Memo>
    lateinit var adapter: MemoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //테이블에 저장한 데이터 불러오기
        datas = mutableListOf<Memo>()
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from memo_t",null)
        while(cursor.moveToNext()){
            val date = cursor.getString(2)
            val memo = cursor.getString(1)
            datas.add(Memo(memo,date))
        }
        db.close()

        adapter= MemoAdapter(datas)
        binding.recyclerView.adapter=adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == RESULT_OK && data != null) {
                val date = data.getStringExtra("today")
                val memoText = data.getStringExtra("result")
                if (!memoText.isNullOrEmpty() && date != null) {
                    datas.add(Memo(memoText, date))
                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddMemoActivity::class.java)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") // 년 월 일
            intent.putExtra("today",dateFormat.format(System.currentTimeMillis()))

            requestLauncher.launch(intent)
        }
    }
}