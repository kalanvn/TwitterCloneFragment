package com.kalan.android.twitterclonefragment

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.kalan.android.twitterclonefragment.database.Tweet
import com.kalan.android.twitterclonefragment.database.TweetDao
import com.kalan.android.twitterclonefragment.database.TwitterDatabase
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TwitterDatabaseTest {

    private lateinit var tweetDao: TweetDao
    private lateinit var db: TwitterDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, TwitterDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        tweetDao = db.tweetDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        val tweet = Tweet(text = "test")
        tweetDao.insert(tweet)
        val latestTweet = tweetDao.getLatest()
        Assert.assertEquals(latestTweet?.text, "test")
    }
}