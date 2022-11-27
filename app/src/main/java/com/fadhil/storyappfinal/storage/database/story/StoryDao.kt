package com.fadhil.storyappfinal.storage.database.story

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fadhil.storyappfinal.data.GetStoryResult


@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addStories(stories: List<GetStoryResult>)

    @Query("SELECT * FROM story")
    fun getStories(): PagingSource<Int, GetStoryResult>

    @Query("DELETE FROM story")
    suspend fun clearStory()
}