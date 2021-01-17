package com.kalan.android.twitterclonefragment.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao //Data access object
interface TweetDao {

    //Create
    @Insert
    suspend fun insert(tweet: Tweet)

    //Read
    @Query("SELECT * from Tweet WHERE tweetId = :key")
    fun get(key: Long): Tweet?

    @Query("SELECT * from Tweet ORDER BY tweetId DESC")
    fun getAllTweets(): LiveData<List<Tweet>>

    @Query("SELECT * from Tweet ORDER BY tweetId DESC LIMIT 1")
    fun getLatest(): Tweet?

    //Update
    @Update
    fun update(tweet: Tweet)

    //Delete
//    @Delete
    @Query("DELETE from Tweet")
    suspend fun clear()

    //CRUD operations
}