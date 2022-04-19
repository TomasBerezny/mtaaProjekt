package com.example.kdesikamos

import com.example.kdesikamos.dto.*
import retrofit2.Call
import retrofit2.http.*

interface DbApi {

    @GET("activities/home")
    fun getActivities() : Call<List<ActivityDTO>>

    @POST("activities")
    fun postActivity(@Body activity: ActivityRequest) : Call<ActivityRequest>

    @POST("usersActivities")
    fun addUserToActivity(@Body activity: UserActivity) : Call<UserActivity>

    @GET("categories")
    fun getCategories() : Call<List<CategoryDTO>>

    @POST("auth/login")
    fun login(@Body body: LoginRequest) : Call<UserDTO>

    @POST("auth/register")
    fun register(@Body body: RegisterRequest) : Call<UserDTO>

    @PATCH("users/{userId}")
    fun saveSettings(@Path("userId") userId: String, @Body body: Settings) : Call<UserDTO>

}