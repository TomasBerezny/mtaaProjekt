package com.example.kdesikamos.dto

import java.util.*

data class ActivityRequest(val _id: String,
                       val category_id: String,
                       val place: String,
                       var user_id: String,
                       val description: String,
                       val date: Date,
                           val share_phone: Boolean,
                           val as_group: Boolean,
                           val group_id: String,

)