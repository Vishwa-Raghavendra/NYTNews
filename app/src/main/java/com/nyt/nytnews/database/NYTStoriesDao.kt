package com.nyt.nytnews.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nyt.nytnews.models.Result

@Dao
interface NYTStoriesDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(data :List<Result>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: Result)

    @Query(value = "SELECT * FROM nyt_stories")
    suspend fun getAllStories() : List<Result>

    @Query(value = "SELECT * FROM nyt_stories WHERE bookmarked = 1")
    suspend fun getBookMarks():List<Result>
}