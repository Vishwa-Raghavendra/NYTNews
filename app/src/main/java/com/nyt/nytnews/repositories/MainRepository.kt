package com.nyt.nytnews.repositories

import com.nyt.nytnews.database.NYTStoriesDao
import com.nyt.nytnews.models.Result
import com.nyt.nytnews.utility.RetrofitInstance

class MainRepository(private val nytStoriesDao: NYTStoriesDao)
{
    suspend fun getTopStories():List<Result>
    {

        val body = RetrofitInstance.nytTopStoriesAPI.getTopStories()
        return body.body()!!.results
    }

    suspend fun storeData(data:List<Result>)
    {
        nytStoriesDao.insertAll(data)
    }

    suspend fun getOfflineData():List<Result>
    {
        return nytStoriesDao.getAllStories()
    }
}