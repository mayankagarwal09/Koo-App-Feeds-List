package com.example.kooapp.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse(val meta: Meta, val data: List<Post>)

data class Meta(val pagination: Pagination)

data class Pagination(
    val total: Long,
    val pages: Int,
    val page: Int,
    val limit: Int,
    val links: Links
)

data class Links(val previous: String?, val current: String, val next: String?)