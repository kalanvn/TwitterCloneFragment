package com.kalan.android.twitterclonefragment.tweets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kalan.android.twitterclonefragment.R
import com.kalan.android.twitterclonefragment.database.Tweet
import com.kalan.android.twitterclonefragment.database.TweetDao
import com.kalan.android.twitterclonefragment.model.TweetData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TweetsViewModel (val tweetDao: TweetDao) : ViewModel() {
    companion object {
        private const val TAG = "TweetsViewModel"
    }
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

    fun loadTweetsFromFirebaseToDb() {
        val firestore = Firebase.firestore
        viewModelScope.launch {
            firestore.collection("tweets")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d(TAG, "${document.id} => ${document.data}")
                        val hashMap = document.data
                        val tweet = Tweet(text = hashMap["text"] as String
                        , postedTime = hashMap["postedTime"] as Long
                        , tweetId = hashMap["tweetId"] as Long)
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                tweetDao.insert(tweet)
                            }
                        }

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
        }
    }
}