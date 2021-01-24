package com.kalan.android.twitterclonefragment.tweetdetails

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalan.android.twitterclonefragment.database.Tweet
import com.kalan.android.twitterclonefragment.database.TweetDao
import kotlinx.coroutines.launch

class TweetDetailsViewModel (tweetId : Long?, val tweetDao: TweetDao): ViewModel(){
//    var tweetName = tweetNameInput
//    var tweetNameLiveData = MutableLiveData<String>()
//    var handler = Handler()
//    init {
//        tweetNameLiveData.value = tweetName
//        handler.postDelayed(
//            Runnable {
//                tweetNameLiveData.value = "Changed value after 5 seconds"
//            }, 5* 1000
//        )
//    }
//

    //The internal MutableLiveData string that stores the most recent tweet
    private val _tweet = MutableLiveData<Tweet>()

    //The external immutable LiveData for the tweet
    val tweet : LiveData<Tweet>
        get() = _tweet

    fun loadTweetFromDb(tweetId: Long){
        //retrieve tweet from db using tweet id
        //update private mutable live data
        //for file operations 20ms
        //cpu cycle 1 microsecond / or nano seconds
        //1000ms/60Hz ~ 16 ms main thread loads one frame
        //if we do file operation on main thread, frames will be skipped
        //we will do file/database/network operations in another thread.
        //Here we will use Coroutines. Coroutines run on a thread pool.
        viewModelScope.launch {
            _tweet.value = tweetDao.get(tweetId)
        }
    }


    fun loadTweetFromDbLive(tweetId: Long) : LiveData<Tweet?>{
        return tweetDao.getLive(tweetId)
    }

}