package com.example.kooapp.api

import com.example.kooapp.models.ApiResponse

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/*
private fun getLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
}
 */

val client = OkHttpClient.Builder()
    //.addInterceptor(getLoggingInterceptor())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client)
    .baseUrl(PostApi.BASE_URL)
    .build()

interface PostApiService {

    @GET("public/v1/posts")
    suspend fun getPosts(): ApiResponse

    @GET("public/v1/posts")
    suspend fun getPostsFromPage(@Query("page") page: Int): ApiResponse

}

object PostApi {
    const val BASE_URL = "https://gorest.co.in/"

    val retrofitService: PostApiService by lazy {
        retrofit.create(PostApiService::class.java)
    }
}