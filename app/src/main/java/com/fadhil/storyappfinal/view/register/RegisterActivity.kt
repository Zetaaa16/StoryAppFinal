package com.fadhil.storyappfinal.view.register

import android.animation.AnimatorSet
import com.fadhil.storyappfinal.data.Result
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.fadhil.storyappfinal.databinding.ActivityRegisterBinding
import com.fadhil.storyappfinal.utils.ViewModelFactory
import com.fadhil.storyappfinal.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val signUpViewModel: RegisterViewModel by viewModels {
            factory
        }

        binding.signupButton.setOnClickListener {
            signUpViewModel.postRegister(
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            ).observe(this) { result ->
                when(result) {
                    is Result.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressbar.visibility = View.GONE
                        Toast.makeText(this, "Sign Up ${result.data.message}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    }
                    is Result.Error -> {
                        binding.progressbar.visibility = View.GONE
                        Toast.makeText(this, "Sign Up ${result.error}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        playAnimation()

        supportActionBar?.hide()
    }

    private fun playAnimation(){
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)
        val already = ObjectAnimator.ofFloat(binding.textalready, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.login, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(already,login)
        }

        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup,
                together

            )
            startDelay = 500
        }.start()
    }
}