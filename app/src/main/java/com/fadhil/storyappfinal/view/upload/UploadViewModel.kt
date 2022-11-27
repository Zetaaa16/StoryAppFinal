package com.fadhil.storyappfinal.view.upload

import androidx.lifecycle.ViewModel
import com.fadhil.storyappfinal.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UploadViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun uploadImage(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double,
        lon: Double,
        token: String
    ) = storyRepository.uploadImage(file, description, lat, lon, token)
}