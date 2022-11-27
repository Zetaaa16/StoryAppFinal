package com.fadhil.storyappfinal.view.login

import androidx.lifecycle.ViewModel
import com.fadhil.storyappfinal.data.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postLogin(email: String, pass: String) = storyRepository.postLogin(email, pass)
}