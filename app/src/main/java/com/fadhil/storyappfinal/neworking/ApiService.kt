package com.fadhil.storyappfinal.neworking

import com.fadhil.storyappfinal.data.GetStoryResponse
import com.fadhil.storyappfinal.data.LoginResponse
import com.fadhil.storyappfinal.data.RegisterResponse
import com.fadhil.storyappfinal.data.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun Register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun Login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") auth: String
    ): GetStoryResponse

    @GET("stories")
    suspend fun getAllStoriesWithLocation(
        @Query("location") loc: Int,
        @Header("Authorization") auth: String
    ): GetStoryResponse

    @Multipart
    @POST("stories")
    suspend fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?,
        @Header("Authorization") auth: String
    ): UploadResponse
}