package com.fadhil.storyappfinal.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fadhil.storyappfinal.data.GetStoryResult
import com.fadhil.storyappfinal.data.StoryRepository


class MainViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStory(token: String) : LiveData<PagingData<GetStoryResult>> =
        storyRepository.getStories(token).cachedIn(viewModelScope)
}