package com.example.beeceptorarticle

import com.example.beeceptorarticle.Model.Article
import com.example.beeceptorarticle.Model.ArticleDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiEndPoint {
    @GET("article")
    fun getArticleData(): Call<List<Article>>

    @GET("article/{id}")
    fun getArticleDetailData(
        @Path("id") id: Int): Call<ArticleDetails>
}