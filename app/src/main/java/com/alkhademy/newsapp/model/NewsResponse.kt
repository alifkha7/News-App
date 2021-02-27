package com.alkhademy.newsapp.model



data class NewsResponse(
    val articles: ArrayList<Article>,
    val status: String,
    val totalResults: Int,
    val code: String,
    val message: String
)