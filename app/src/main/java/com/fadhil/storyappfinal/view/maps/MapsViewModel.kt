package com.fadhil.storyappfinal.view.maps

import androidx.lifecycle.ViewModel
import com.fadhil.storyappfinal.data.StoryRepository


class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getStoryWithMaps(location: Int, token: String) =
        storyRepository.getStoryWithMaps(location, token)
}