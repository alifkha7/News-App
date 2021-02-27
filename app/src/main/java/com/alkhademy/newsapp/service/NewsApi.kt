package com.alkhademy.newsapp.service

import com.alkhademy.newsapp.model.NewsResponse
import retrofit2.Call
import retrofit2.http.*

interface NewsApi {
    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String, @Header("Authorization") authorization: String): Call<NewsResponse>
    @GET("top-headlines")
    fun getTopHeadlinesCategory(@Query("country") country: String, @Query("category") category: String, @Header("Authorization") authorization: String): Call<NewsResponse>
}