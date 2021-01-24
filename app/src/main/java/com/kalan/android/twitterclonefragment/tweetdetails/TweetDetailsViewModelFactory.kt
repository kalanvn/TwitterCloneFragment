package com.kalan.android.twitterclonefragment.tweetdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kalan.android.twitterclonefragment.database.TweetDao
import java.lang.IllegalArgumentException

class TweetDetailsViewModelFactory (val tweetId : Long?, val tweetDao: TweetDao)
    :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TweetDetailsViewModel::class.java)) {
            return TweetDetailsViewModel(tweetId, tweetDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
