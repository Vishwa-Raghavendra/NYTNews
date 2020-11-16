package com.nyt.nytnews.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nyt.nytnews.repositories.MainRepository

class MainViewModelFactory(private val mainRepository: MainRepository, private val context: Context) : ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepository,context) as T
    }
}