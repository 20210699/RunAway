package com.example.runaway

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.runaway.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {

    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeVisibility(intent.getStringExtra("status").toString())

        binding.goSignUpBtn.setOnClickListener {
            changeVisibility("signup")
        }
        binding.goSignInBtn.setOnClickListener {    // 회원 가입 Button
            changeVisibility("signin")
        }
        binding.signUpBtn.setOnClickListener {    // 가입 Button
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()
            MyApplication.auth.createUserWithEmailAndPassword(email,password) // 중요한 함수
                .addOnCompleteListener(this){task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if(task.isSuccessful){
                        MyApplication.auth.currentUser?.sendEmailVerification() // 이메일 인증 버튼
                            ?.addOnCompleteListener{sendTask ->
                                if(sendTask.isSuccessful){
                                    Toast.makeText(baseContext,"회원가입 성공!!.. 메일을 확인해주세요",Toast.LENGTH_SHORT).show()
                                    Log.d("mobileapp", "회원가입 성공!!")
                                    changeVisibility("logout") //회원가입만 성공한 상태, 로그인은 아직
                                }
                                // 메일 확인 버튼 안누름
                                else{
                                    Toast.makeText(baseContext,"메일발송 실패",Toast.LENGTH_SHORT).show()
                                    Log.d("mobileapp", "메일발송 실패")
                                    changeVisibility("logout")
                                }
                            }
                    }
                    // 회원 가입 자체를 실패
                    else{
                        Toast.makeText(baseContext,"회원가입 실패",Toast.LENGTH_SHORT).show()
                        Log.d("mobileapp", "== ${task.exception} ==")
                        changeVisibility("logout")
                    }
                }
        }

        binding.loginBtn.setOnClickListener {   // 로그인 Button
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()
            MyApplication.auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if(task.isSuccessful){
                        if(MyApplication.checkAuth()){
                            MyApplication.email = email
                            Log.d("mobileapp", "로그인 성공")
                            finish()
                        }
                        else{
                            Toast.makeText(baseContext,"이메일 인증이 되지 않았습니다.",Toast.LENGTH_SHORT).show()
                            Log.d("mobileapp", "이메일 인증 안됨")
                        }
                    }
                    else{
                        Toast.makeText(baseContext,"로그인 실패",Toast.LENGTH_SHORT).show()
                        Log.d("mobileapp", "로그인 실패")
                    }
                }
        }

        binding.logoutBtn.setOnClickListener {  // 로그아웃 Button
            MyApplication.auth.signOut()
            MyApplication.email = null
            Log.d("mobileapp", "로그 아웃")
            finish()
        }

        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            Log.d("mobileapp","account1 : ${task.toString()}")
            //Log.d("mobileapp","account2 : ${task.result}")
            try{
                val account = task.getResult(ApiException::class.java)
                val crendential = GoogleAuthProvider.getCredential(account.idToken, null) // 한 번 로그인하면 여러번 로그인 안해도 됨.
                MyApplication.auth.signInWithCredential(crendential)
                    .addOnCompleteListener(this){task ->
                        if(task.isSuccessful){
                            MyApplication.email = account.email
                            Toast.makeText(baseContext,"구글 로그인 성공",Toast.LENGTH_SHORT).show()
                            Log.d("mobileapp", "구글 로그인 성공")
                            finish()
                        }
                        else{
                            changeVisibility("logout")
                            Toast.makeText(baseContext,"구글 로그인 실패",Toast.LENGTH_SHORT).show()
                            Log.d("mobileapp", "구글 로그인 실패")
                        }
                    }
            }catch (e: ApiException){ // APIException은 이미 지정된 exception말고 custom한 exception을 만들어서 쓰고 싶을때 사용
                changeVisibility("logout")
                Toast.makeText(baseContext,"구글 로그인 Exception : ${e.printStackTrace()},${e.statusCode}",Toast.LENGTH_SHORT).show()
                Log.d("mobileapp", "구글 로그인 Exception : ${e.message}, ${e.statusCode}")
            }
        }

        binding.loginGoogle.setOnClickListener { // 구글인증 Button
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_client_id))
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this,gso).signInIntent
            requestLauncher.launch(signInIntent)
        }


    }

    fun changeVisibility(mode: String){
        if(mode.equals("signup")){ // 회원가입을 하는 상태
            binding.run {
                goSignInBtn.visibility = View.GONE
                goSignUpBtn.visibility = View.GONE

                authTextViewSignUp.visibility = View.VISIBLE
                authTextViewLogIn.visibility = View.GONE

                authEmailEditView.visibility= View.VISIBLE
                authPasswordEditView.visibility= View.VISIBLE
                authConfirmPasswordEditView.visibility= View.VISIBLE
                signUpBtn.visibility= View.VISIBLE
                loginBtn.visibility = View.GONE

                lineTextLogin.visibility = View.GONE
                lineTextSignup.visibility = View.VISIBLE

                loginWithBtn.visibility = View.VISIBLE
            }

        }else if(mode.equals("login")) { //로그인 상태 -> 로그아웃을 할 수 있도록 해줌
            binding.run {
                goSignInBtn.visibility = View.GONE
                goSignUpBtn.visibility = View.GONE

                authTextViewSignUp.text = "Log Out"
                authTextViewSignUp.visibility = View.VISIBLE
                authConfirmPasswordEditView.visibility= View.GONE
                lineTextSignup.visibility = View.GONE
                signUpBtn.visibility= View.GONE

                authTextViewLogIn.visibility=View.GONE
                authEmailEditView.visibility= View.GONE
                authPasswordEditView.visibility= View.GONE
                loginBtn.visibility= View.GONE
                lineTextLogin.visibility= View.GONE
                loginWithBtn.visibility= View.GONE
                logoutBtn.visibility=View.VISIBLE
            }

        } else if(mode.equals("logout")){ // 로그아웃인 상태 -> 로그인을 할 수 있도록 해줌
            binding.run {

                goSignInBtn.visibility = View.GONE
                goSignUpBtn.visibility = View.VISIBLE

                authTextViewSignUp.visibility = View.GONE
                authConfirmPasswordEditView.visibility= View.GONE
                lineTextSignup.visibility = View.GONE
                signUpBtn.visibility= View.GONE

                authTextViewLogIn.visibility = View.VISIBLE
                authEmailEditView.visibility= View.VISIBLE
                authPasswordEditView.visibility= View.VISIBLE
                loginBtn.visibility= View.VISIBLE
                lineTextLogin.visibility= View.VISIBLE
                loginWithBtn.visibility= View.VISIBLE
            }
        }
    }
}