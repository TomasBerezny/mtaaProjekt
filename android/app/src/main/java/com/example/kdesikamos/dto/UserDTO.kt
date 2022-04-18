package com.example.kdesikamos.dto

import java.util.*

data class UserDTO(val _id: String,
                       val username: String,
                       val password: String,
                       var first_name: String,
                       val last_name: String,
                   val profile_photo_path: String,
                   val bio: String,
                   val phone_number: String,
)