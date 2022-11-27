package com.fadhil.storyappfinal.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import com.fadhil.storyappfinal.data.Result
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.fadhil.storyappfinal.databinding.ActivityLoginBinding
import com.fadhil.storyappfinal.storage.pref.PreferenceDataSource
import com.fadhil.storyappfinal.storage.pref.UserModel
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_KEY
import com.fadhil.storyappfinal.utils.ViewModelFactory
import com.fadhil.storyappfinal.view.main.MainActivity
import com.fadhil.storyappfinal.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var userModel: UserModel = UserModel()
    private lateinit var pref: PreferenceDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val loginViewModel: LoginViewModel by viewModels {
            factory
        }

        pref = PreferenceDataSource(this)

        binding.register.setOnClickListener {
            moveToSignUp()
        }

        binding.loginButton.setOnClickListener {
            loginViewModel.postLogin(
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding.progressbar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressbar.visibility = View.GONE
                            Toast.makeText(this, "Login ${result.data.message}", Toast.LENGTH_SHORT)
                                .show()
                            val response = result.data
                            saveToken(response.loginResult.token)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra(EXTRA_KEY, response.loginResult.token)
                            startActivity(intent)
                        }
                        is Result.Error -> {
                            binding.progressbar.visibility = View.GONE
                            Toast.makeText(this, "Login ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        playAnimation()

        supportActionBar?.hide()
    }

    private fun moveToSignUp() {
        intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val message =
            ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.register, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login,
                register
            )
            startDelay = 500
        }.start()
    }

    private fun saveToken(token: String) {
        userModel.token = token
        pref.setUser(userModel)
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}