package com.example.runaway

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.runaway.databinding.ActivitySplashBinding
import android.animation.Animator
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animator: Animator) {
                // Not needed for this scenario, but must be implemented
            }

            override fun onAnimationEnd(animator: Animator) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationCancel(animator: Animator) {
                // Handle animation cancel
            }

            override fun onAnimationStart(animator: Animator) {
                // Handle animation start
            }
        })

        // Optional: Start the animation if not set to play automatically
        binding.lottie.playAnimation()
    }
}
