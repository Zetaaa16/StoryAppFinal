package com.fadhil.storyappfinal.view.register

import androidx.lifecycle.ViewModel
import com.fadhil.storyappfinal.data.StoryRepository


class RegisterViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postRegister(name: String, email: String, pass: String) =
        storyRepository.postRegister(name, email, pass)
}