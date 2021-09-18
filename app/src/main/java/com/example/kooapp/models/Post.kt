package com.example.kooapp.models

import com.squareup.moshi.Json

data class Post(
    val id: Long,
    @Json(name = "user_id") val userId: Long,
    val title: String,
    val body: String
)

