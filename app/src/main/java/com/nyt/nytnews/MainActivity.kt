package com.nyt.nytnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nyt.nytnews.fragments.HomeFragment
import com.nyt.nytnews.utility.RetrofitInstance
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.mainFragContainer,HomeFragment()).commit()
    }
}