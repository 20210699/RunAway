package com.example.runaway

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.runaway.databinding.ActivityShalterDetailBinding

class ShalterDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityShalterDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShalterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}