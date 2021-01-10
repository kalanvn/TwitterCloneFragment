package com.kalan.android.twitterclonefragment.tweetdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class TweetDetailsViewModelFactory (val tweetName : String?)
    :ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TweetDetailsViewModel::class.java)) {
            return TweetDetailsViewModel(tweetName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
