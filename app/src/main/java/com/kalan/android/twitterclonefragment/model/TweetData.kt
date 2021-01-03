package com.kalan.android.twitterclonefragment.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class TweetData(
        var tweetText: String,
        @DrawableRes val tweetImageId: Int,
        @StringRes val tweetTextId: Int
)
