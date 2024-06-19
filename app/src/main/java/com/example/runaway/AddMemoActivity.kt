package com.example.runaway

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.runaway.databinding.ActivityAddMemoBinding
import java.io.File
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class AddMemoActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddMemoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var date = intent.getStringExtra("today")
        binding.date.text = date

        binding.btnSave.setOnClickListener {
            val edt_srt = binding.addEditView.text.toString()
            val intent = intent
            intent.putExtra("result", edt_srt)
            setResult(Activity.RESULT_OK, intent)

            /// db에 저장하기
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into memo_t (memo,date) values (?,?)", arrayOf<String>(edt_srt, date.toString())) //테이블에 데이터 넣기
            db.close()

            finish()
            true
        }
    }
}