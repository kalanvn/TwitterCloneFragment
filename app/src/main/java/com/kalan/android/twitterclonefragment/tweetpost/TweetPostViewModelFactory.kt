package com.kalan.android.twitterclonefragment.tweetpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kalan.android.twitterclonefragment.database.TweetDao

class TweetPostViewModelFactory (val tweetDao: TweetDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TweetPostViewModel::class.java)){
            return TweetPostViewModel(tweetDao) as T
        }
        throw IllegalArgumentException("Unknown View Model")
    }
}