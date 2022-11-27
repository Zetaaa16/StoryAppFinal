package com.fadhil.storyappfinal.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fadhil.storyappfinal.data.Injection
import com.fadhil.storyappfinal.data.StoryRepository
import com.fadhil.storyappfinal.view.login.LoginViewModel
import com.fadhil.storyappfinal.view.main.MainViewModel
import com.fadhil.storyappfinal.view.maps.MapsViewModel
import com.fadhil.storyappfinal.view.register.RegisterViewModel
import com.fadhil.storyappfinal.view.upload.UploadViewModel


class ViewModelFactory private constructor(private val storyRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                return MapsViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                return UploadViewModel(storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}