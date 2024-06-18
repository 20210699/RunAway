package com.example.runaway

import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runaway.databinding.ActivityBottomSheetBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}