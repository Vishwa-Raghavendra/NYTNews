package com.nyt.nytnews.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nyt.nytnews.models.Result
import com.nyt.nytnews.repositories.MainRepository
import com.nyt.nytnews.utility.Resource
import com.nyt.nytnews.utility.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


class MainViewModel(private val mainRepository: MainRepository, private val context: Context) :
    ViewModel() {

    //LiveData for NYT Top Stories
    private val _topStories = MutableLiveData<Resource<List<Result>>>()
    val topStories: LiveData<Resource<List<Result>>>
        get() = _topStories

    //LiveData for BookMarked Stories
    private val _bookmarks = MutableLiveData<List<Result>>()
    val bookmarks: LiveData<List<Result>>
        get() = _bookmarks

    //Stores the data of the clicked viewHolder for fragment to fragment communication
    lateinit var clickedResult :Result

    //To Convert API Date to human readable form
    var originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH)
    var targetFormat: DateFormat = SimpleDateFormat("yyyy MMM dd hh:mm:a",Locale.ENGLISH)

    init {
        getStories()
    }

    fun getBookmarks()
    {
       viewModelScope.launch(Dispatchers.IO) {
           _bookmarks.postValue(mainRepository.getDao().getBookMarks())
       }
    }

     fun getStories() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                if (Utility.hasInternetConnection(context)) {
                    getDataFromServer()
                } else {
                    //Loads data from Room Database(stored articles)
                    val list = mainRepository.getOfflineData()
                    if(list.isNotEmpty())
                    _topStories.postValue(Resource.Success(data = list))
                }
            } catch (e: Exception) {
                _topStories.postValue(Resource.Error(message = "" + e.localizedMessage))
            }
        }
    }

    //To get NYT stories from API
    private suspend fun getDataFromServer() {
        try {

            val data = mainRepository.getTopStories().toMutableList()


            //Gets the primary keys from database which is used to check if an article is bookmarked so as to change bookmark image
            val bookMarkedPrimaryKeysList = mainRepository.getDao().getPrimaryKeys()
            val bookMarkedPrimaryKeys = HashSet<String>(bookMarkedPrimaryKeysList)

            //Iterates over the list to convert given date to human readable date

            for (x in data.indices)
            {
                val element = data[x]
                val date = originalFormat.parse(element.updatedDate)!!
                element.displayDate = targetFormat.format(date)

                if(bookMarkedPrimaryKeys.contains(element.shortUrl))
                    element.bookmarked = true

                data[x] = element
            }

            _topStories.postValue(Resource.Success(data = data))
            mainRepository.storeData(data)
        } catch (e: Exception) {
            _topStories.postValue(Resource.Error(message = "" + e.localizedMessage))
            _topStories.postValue(Resource.Success(data = mainRepository.getOfflineData()))
        }
    }

    //Used to Change the object when user bookmarks an article
    fun updateStory(result: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.updateStory(result)
        }
    }

    //Method Executed when user de-selects an article from Bookmarked fragment
    fun removeBookMark(result: Result)
    {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.updateStory(result)
            getBookmarks()
            fixTopStories(result)
        }

    }

    //ReSubmits the loaded list to visualize the bookmark button ui changes on articles which have been de-Bookmarked by user
    private  fun fixTopStories(result: Result) {

        if(_topStories.value is Resource.Success)
        {
            val list = ArrayList<Result>(topStories.value!!.data!!)

            for( x in 0 until list.size)
            {
                val data = list[x]

                if(data.shortUrl == result.shortUrl)
                {
                    result.bookmarked = false
                    list[x] = result
                }
            }
            _topStories.postValue(Resource.Success(data = list))
        }
    }
}