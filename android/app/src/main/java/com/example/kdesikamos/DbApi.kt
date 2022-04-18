package com.example.kdesikamos

import com.example.kdesikamos.dto.ActivityDTO
import com.example.kdesikamos.dto.LoginRequest
import com.example.kdesikamos.dto.RegisterRequest
import com.example.kdesikamos.dto.UserDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DbApi {

    @GET("activities/home")
    fun getActivities() : Call<List<ActivityDTO>>

    @POST("auth/login")
    fun login(@Body body: LoginRequest) : Call<UserDTO>

    @POST("auth/register")
    fun register(@Body body: RegisterRequest) : Call<UserDTO>

}