package com.example.beeceptorarticle.ViewModel

import androidx.lifecycle.ViewModel
import com.example.beeceptorarticle.ApiEndPoint
import com.example.beeceptorarticle.Model.Article
import com.example.beeceptorarticle.Model.ArticleDetails
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleViewModel : ViewModel {

    constructor() : super()

    companion object {
        var BaseUrl = "https://task.free.beeceptor.com/"
    }

    val gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl(BaseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val service = retrofit.create(ApiEndPoint::class.java)


    fun getArticleFromApi(): Call<List<Article>> {
        return service.getArticleData()
    }

    fun getArticleDetailsFromApi(id: Int): Call<ArticleDetails> {
        return service.getArticleDetailData(id)
    }


}