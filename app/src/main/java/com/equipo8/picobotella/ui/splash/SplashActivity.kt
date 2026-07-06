package com.equipo8.picobotella.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.equipo8.picobotella.MainActivity
import com.equipo8.picobotella.R
import com.equipo8.picobotella.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val splashDelayMillis = 5000L
    private val splashHandler = Handler(Looper.getMainLooper())

    private val goToMainRunnable = Runnable {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        startEntranceAnimations(binding.imgSplashBotella, binding.tvSplashTitle)
        splashHandler.postDelayed(goToMainRunnable, splashDelayMillis)
    }

    override fun onDestroy() {
        splashHandler.removeCallbacks(goToMainRunnable)
        super.onDestroy()
    }

    private fun startEntranceAnimations(imageView: ImageView, titleView: TextView) {
        imageView.startAnimation(
            AnimationUtils.loadAnimation(this, android.R.anim.fade_in).apply {
                duration = 900
                interpolator = AccelerateDecelerateInterpolator()
            }
        )

        titleView.startAnimation(
            AnimationUtils.loadAnimation(this, android.R.anim.fade_in).apply {
                startOffset = 250
                duration = 900
                interpolator = AccelerateDecelerateInterpolator()
            }
        )

        imageView.animate()
            .scaleX(1.08f)
            .scaleY(1.08f)
            .setDuration(1000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setStartDelay(250)
            .withEndAction {
                imageView.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(1000)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .start()
            }
            .start()
    }
}