package com.nyt.nytnews.utility

import com.nyt.nytnews.apis.NYTTopStoriesAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance
{
    companion object
    {
        private val retrofit by lazy {
            val okHttpConnectClient =
                OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS).build()

            Retrofit.Builder()
                .client(okHttpConnectClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val nytTopStoriesAPI :NYTTopStoriesAPI by lazy {
            retrofit.create(NYTTopStoriesAPI::class.java)
        }
    }

}