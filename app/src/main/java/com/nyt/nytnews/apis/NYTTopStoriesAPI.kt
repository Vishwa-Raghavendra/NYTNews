package com.nyt.nytnews.apis

import com.nyt.nytnews.models.NYTStoriesModel
import com.nyt.nytnews.utility.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NYTTopStoriesAPI
{
    @GET("svc/topstories/v2/arts.json")
    suspend fun getTopStories(@Query("api-key")key:String=Constants.API_KEY) :Response<NYTStoriesModel>
}