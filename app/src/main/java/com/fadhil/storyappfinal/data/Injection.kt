package com.fadhil.storyappfinal.data

import android.content.Context
import com.fadhil.storyappfinal.neworking.ApiConfig
import com.fadhil.storyappfinal.storage.database.story.StoryDatabase


object Injection {
    fun provideRepository(context: Context) : StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}