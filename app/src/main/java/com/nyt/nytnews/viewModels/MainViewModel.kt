package com.nyt.nytnews.viewModels

import android.content.Context
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nyt.nytnews.models.Result
import com.nyt.nytnews.repositories.MainRepository
import com.nyt.nytnews.utility.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import androidx.lifecycle.viewModelScope


class MainViewModel(private val mainRepository: MainRepository, private val context: Context) :
    ViewModel() {
    private val _topStories = MutableLiveData<Resource<List<Result>>>()
    val topStories: LiveData<Resource<List<Result>>>
        get() = _topStories

    init {
        getStories()
    }

    private fun getStories() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                if (hasInternetConnection()) {
                   getDataFromServer()
                } else {
                    _topStories.postValue(Resource.Success(data = mainRepository.getOfflineData()))
                }
            } catch (e: Exception) {
                _topStories.postValue(Resource.Error(message = "" + e.localizedMessage))
            }
        }
    }

    private suspend fun getDataFromServer()
    {
        try {
            val data = mainRepository.getTopStories()
            _topStories.postValue(Resource.Success(data = data))
            mainRepository.storeData(data)
        }catch (e:Exception)
        {
            _topStories.postValue(Resource.Error(message = ""+"Something Went Wrong"))
            _topStories.postValue(Resource.Success(data = mainRepository.getOfflineData()))
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun updateStory(result: Result)
    {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.updateStory(result)
        }
    }
}