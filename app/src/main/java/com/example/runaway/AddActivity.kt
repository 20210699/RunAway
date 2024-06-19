package com.example.runaway

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.runaway.databinding.ActivityAddBinding
import java.text.SimpleDateFormat

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var uri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvId.text = MyApplication.email

        // 이미지 불러오기
        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode === android.app.Activity.RESULT_OK){ // 성공적으로 가져옴
                binding.addImageView.visibility = View.VISIBLE
                Glide
                    .with(applicationContext)
                    .load(it.data?.data)
                    .override(200,150)
                    .into(binding.addImageView)
                uri = it.data?.data!!
            }
        }

        binding.uploadButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
            requestLauncher.launch(intent)
        }

        binding.saveButton.setOnClickListener {
            if (binding.input.text.isNotEmpty()){
                //이메일,  스타, 한줄평, 입력 시간
                val dateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                val data = mapOf( // "firestore의 필드명" to 실제 데이터
                    "email" to MyApplication.email,
                    "star" to binding.ratingBar.rating.toFloat(),
                    "comments" to binding.input.text.toString(),
                    "date_time" to dateFormat.format(System.currentTimeMillis())
                )
                MyApplication.db.collection("comments")
                    .add(data)
                    .addOnSuccessListener {
                        Toast.makeText(this, "데이터 저장 성공", Toast.LENGTH_LONG).show()
                        uploadImage(it.id)
                        finish()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"데이터 저장 실패", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this,"한줄평을 먼저 입력해주세요..",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun uploadImage(docId : String) {
        val imageRef = MyApplication.storage.reference.child("images/${docId}.jpg")

        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            Toast.makeText(this,"사진 업로드 성공",Toast.LENGTH_LONG).show()
        }
        uploadTask.addOnFailureListener{
            Toast.makeText(this,"사진 업로드 실패",Toast.LENGTH_LONG).show()
        }

    }
}