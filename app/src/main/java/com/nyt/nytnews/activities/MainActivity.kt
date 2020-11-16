package com.nyt.nytnews.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.nyt.nytnews.R
import com.nyt.nytnews.database.NYTDatabase
import com.nyt.nytnews.fragments.HomeFragment
import com.nyt.nytnews.repositories.MainRepository
import com.nyt.nytnews.utility.ConnectionLiveData
import com.nyt.nytnews.viewModels.MainViewModel
import com.nyt.nytnews.viewModels.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var  viewModel :MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectionLiveData = ConnectionLiveData(this.applicationContext)
        supportFragmentManager.beginTransaction().add(R.id.mainFragContainer,HomeFragment()).commit()

        val factory = MainViewModelFactory(MainRepository(NYTDatabase(this).getNYTStoriesDao()),this.applicationContext)
        viewModel  = ViewModelProvider(this,factory).get(MainViewModel::class.java)

        connectionLiveData.observe(this,{
            if(it)
                viewModel.getStories()
        })
    }

}