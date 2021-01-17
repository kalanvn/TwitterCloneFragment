package com.kalan.android.twitterclonefragment.tweetpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalan.android.twitterclonefragment.database.Tweet
import com.kalan.android.twitterclonefragment.database.TweetDao
import kotlinx.coroutines.launch

class TweetPostViewModel (val tweetDao: TweetDao): ViewModel() {
    fun onPost(tweet: Tweet) {

        //Launches a new coroutine without blocking the current thread
        viewModelScope.launch {
            //save to db
            insert(tweet)
        }
    }

    private suspend fun insert(tweet: Tweet) {
        tweetDao.insert(tweet)
    }
}