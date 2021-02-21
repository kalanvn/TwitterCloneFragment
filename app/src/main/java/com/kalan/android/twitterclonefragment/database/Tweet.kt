package com.kalan.android.twitterclonefragment.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Tweet (
        @PrimaryKey(/*autoGenerate = true*/)
        var tweetId: Long = 0L,

        var text: String = "",

        var postedTime: Long = System.currentTimeMillis(),

        var url: String?
)