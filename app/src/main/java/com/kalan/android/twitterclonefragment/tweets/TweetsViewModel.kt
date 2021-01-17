package com.kalan.android.twitterclonefragment.tweets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalan.android.twitterclonefragment.R
import com.kalan.android.twitterclonefragment.database.TweetDao
import com.kalan.android.twitterclonefragment.model.TweetData
import kotlinx.coroutines.launch

class TweetsViewModel (val tweetDao: TweetDao) : ViewModel() {
//    val tweetDataListMutable = mutableListOf<TweetData>()
//
//    init {
//        Log.i("TweetsViewModel", "TweetsViewModel created")
//
//        tweetDataListMutable.add(TweetData("Zero List Item", R.drawable.image1, R.string.string1))
//        tweetDataListMutable.add(TweetData("First List Item", R.drawable.image2, R.string.string2))
//        tweetDataListMutable.add(TweetData("Second List Item", R.drawable.image3, R.string.string3))
//    }

    val allTweet = tweetDao.getAllTweets()

    override fun onCleared() {
        super.onCleared()
        Log.i("TweetsViewModel", "TweetsViewModel onCleared")
    }

    fun clearData() {
        viewModelScope.launch {
            clear()
        }

    }

    private suspend fun clear() {
        tweetDao.clear()
    }
}