package com.example.runaway

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MyApplication : MultiDexApplication(){
    companion object{
        lateinit var auth: FirebaseAuth
        var email: String? = null
        lateinit var db: FirebaseFirestore
        lateinit var storage: FirebaseStorage

        fun name() : String? {
            val currentUser = auth.currentUser
            var name : String? = null
            if (currentUser != null) {
                val displayName = currentUser.displayName
                if (!displayName.isNullOrBlank()) {
                    name = displayName
                } else {
                    val parts = email.toString().split("@")
                    if (parts?.size == 2) {
                        name = parts[0]
                    }
                }
            }
            return name
        }

        fun checkAuth(): Boolean {
            var currentUser = auth.currentUser
            if (currentUser != null) {
                email = currentUser.email
                return currentUser.isEmailVerified
            }
            return false
        }
    }

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }
}
