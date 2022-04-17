package com.example.kdesikamos

import com.example.kdesikamos.dto.ActivityDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DbApi {

    @GET("activities/home")
    fun getActivities() : Call<List<ActivityDTO>>

}