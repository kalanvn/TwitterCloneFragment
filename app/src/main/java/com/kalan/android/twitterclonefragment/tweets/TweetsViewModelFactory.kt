package com.kalan.android.twitterclonefragment.tweets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kalan.android.twitterclonefragment.database.TweetDao

class TweetsViewModelFactory (val tweetDao: TweetDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TweetsViewModel::class.java)){
            return TweetsViewModel(tweetDao) as T
        }
        return throw IllegalArgumentException("Unknown View Model")
    }
}