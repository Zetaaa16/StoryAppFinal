package com.fadhil.storyappfinal.view.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.fadhil.storyappfinal.databinding.ActivityWelcomeBinding
import com.fadhil.storyappfinal.storage.pref.PreferenceDataSource
import com.fadhil.storyappfinal.storage.pref.UserModel
import com.fadhil.storyappfinal.view.login.LoginActivity
import com.fadhil.storyappfinal.view.main.MainActivity
import com.fadhil.storyappfinal.view.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var userModel: UserModel
    private lateinit var pref : PreferenceDataSource
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupView()
        playAnimation()

        pref = PreferenceDataSource(this)
        userModel = pref.getUser()

        if (userModel.token != "") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X,-30f,30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA,1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA,1f).setDuration(500)
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA,1f).setDuration(500)
        val desc = ObjectAnimator.ofFloat(binding.descTextView, View.ALPHA,1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(login,signup)
        }

        AnimatorSet().apply {
            playSequentially(title,desc,together)
            start()
        }
    }
}