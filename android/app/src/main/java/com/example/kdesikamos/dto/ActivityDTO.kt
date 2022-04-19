package com.example.kdesikamos.dto

import java.util.*

data class ActivityDTO(val _id: String,
                    val category_name: String,
                    val username: String,
                    var profile_pic: String,
                    val description: String,
                       val date: Date,
                       )