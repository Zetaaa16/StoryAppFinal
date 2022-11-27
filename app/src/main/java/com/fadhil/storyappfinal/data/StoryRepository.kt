package com.fadhil.storyappfinal.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.*
import com.fadhil.storyappfinal.neworking.ApiService
import com.fadhil.storyappfinal.storage.database.story.StoryDatabase
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

import com.fadhil.storyappfinal.data.Result


class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService
)
{
    fun getStories(token: String): LiveData<PagingData<GetStoryResult>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
                storyDatabase.storyDao().getStories()
            }
        ).liveData
    }

    fun postLogin(email: String, pass: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.Login(email, pass)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun postRegister(
        name: String,
        email: String,
        pass: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.Register(name, email, pass)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getStoryWithMaps(location: Int, token: String): LiveData<Result<GetStoryResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getAllStoriesWithLocation(location, token)
                emit(Result.Success(response))
            } catch (e: Exception) {
                Log.d("Signup", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }

    fun uploadImage(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double,
        lon: Double,
        token: String
    ): LiveData<Result<UploadResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.uploadStory(file, description, lat, lon, token)
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.d("Signup", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }
}