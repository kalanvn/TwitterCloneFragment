package com.kalan.android.twitterclonefragment.tweetdetails

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TweetDetailsViewModel (tweetNameInput : String?): ViewModel(){
    var tweetName = tweetNameInput
    var tweetNameLiveData = MutableLiveData<String>()
    var handler = Handler()
    init {
        tweetNameLiveData.value = tweetName
        handler.postDelayed(
            Runnable {
                tweetNameLiveData.value = "Changed value after 5 seconds"
            }, 5* 1000
        )
    }

}