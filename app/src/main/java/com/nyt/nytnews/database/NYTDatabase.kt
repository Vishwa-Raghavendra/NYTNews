package com.nyt.nytnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nyt.nytnews.models.Result

@TypeConverters(Converters::class)
@Database(entities = [Result::class], version = 1,exportSchema = false)
abstract class NYTDatabase : RoomDatabase() {

    abstract fun getNYTStoriesDao():NYTStoriesDao

    companion object {
        @Volatile
        private var INSTANCE: NYTDatabase? = null

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NYTDatabase::class.java,
            "nyt_database.db"
        ).build()
    }
}