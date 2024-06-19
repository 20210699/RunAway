package com.example.runaway

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.runaway.MyApplication.Companion.db
import com.example.runaway.databinding.ItemMainBinding

class JsonViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)
class JsonAdapter (val context: Context, val datas:MutableList<MyJsonItems>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return JsonViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as JsonViewHolder).binding
        val model = datas!![position]

        Log.d("RecyclerView", "Binding data: ${model.shel_nm}, ${model.address}")


        binding.itemName.text = model.shel_nm
        binding.itemAddr.text = model.address
        //binding.itemDistance.text = model.issueGbn
        binding.root.setOnClickListener {
            if (binding.shelterDetail.visibility == View.VISIBLE) {
                // 현재 보이는 상태라면 숨김 처리
                binding.shelterDetail.visibility = View.GONE
            } else {
                // 현재 숨겨진 상태라면 보이도록 처리
                binding.shelterDetail.visibility = View.VISIBLE
            }
        }
        binding.btnStar.setOnClickListener {
            if (binding.btnSave.visibility == View.VISIBLE) {
                // 현재 보이는 상태라면 숨김 처리
                binding.btnSave.visibility = View.GONE
                binding.btnSave1.visibility = View.VISIBLE
            } else if(binding.btnSave1.visibility == View.VISIBLE){
                // 현재 숨겨진 상태라면 보이도록 처리
                binding.btnSave.visibility = View.VISIBLE
                binding.btnSave1.visibility = View.GONE
            }
        }

        if(MyApplication.checkAuth()){
            //장소저장 눌렀을 때 파이어베이스에 저장
            // saveBtn 버튼 클릭 이벤트
            binding.btnSave.setOnClickListener{
                Log.d("SW","버튼 눌림")
                binding.btnSave.visibility = View.GONE
                binding.btnSave1.visibility = View.VISIBLE
                val placeinf = mapOf(
                    "email" to MyApplication.email,
                    "latitude" to model.lat,
                    "longitude" to model.lon,
                    "pname" to model.shel_nm,
                    "paddress" to model.address
                )
                db.collection("place")
                    .add(placeinf)
                    .addOnSuccessListener { documentReference ->
                        Log.d("store", "firebase save : ${documentReference.id}")

                    }
                    .addOnFailureListener { e ->
                        Log.d("store", "firebase save error", e)
                    }
            }

            // saveBtn1 버튼 클릭 이벤트
            binding.btnSave1.setOnClickListener {
                Log.d("SW","버튼 눌림")
                // saveBtn1을 숨기고 saveBtn을 표시
                binding.btnSave.visibility = View.VISIBLE
                binding.btnSave1.visibility = View.GONE

                // 해당 장소를 Firestore에서 삭제하는 코드 추가
                val placeName = model.shel_nm
                val placeRef = db.collection("place").whereEqualTo("pname", placeName)

                placeRef.get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            // Firestore에서 해당 장소 삭제
                            db.collection("place").document(document.id)
                                .delete()
                                .addOnSuccessListener {
                                    Log.d("Firestore", "DocumentSnapshot successfully deleted.")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("Firestore", "삭제 실패", e)
                                }
                        }
                    } else {
                        Log.d("Firestore", "삭제 에러 :", task.exception)
                    }
                }

            }
        } else {
            Toast.makeText(context,"인증을 먼저 진행해주세요",Toast.LENGTH_LONG).show()
        }



    }
}