package com.alkhademy.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alkhademy.newsapp.R
import com.alkhademy.newsapp.model.Article
import com.alkhademy.newsapp.model.NewsResponse
import com.alkhademy.newsapp.service.NewsClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SportsViewModel:ViewModel() {

    val listArticles = MutableLiveData<ArrayList<Article>>()

    fun setSports(country: String, category: String, apiKey: String){
        NewsClient.apiInstance.getTopHeadlinesCategory(country, category, apiKey)
            .enqueue(object : Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful){
                        Log.d("Response",response.message())
                        listArticles.postValue(response.body()?.articles)
                    } else {
                        Log.d("Message", response.body()?.status.toString())
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Log.d("Response",t.message.toString())
                }

            })
    }

    fun getSports(): LiveData<ArrayList<Article>> {
        return listArticles
    }
}