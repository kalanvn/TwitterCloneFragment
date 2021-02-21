package com.kalan.android.twitterclonefragment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//This class is to act as a database holder.
//The class is abstract, because Room creates the implementation for you.
//Supply the Tweet as the only item with the list of entities.
//Set the version as 1**.** Whenever you change the schema, you'll have to increase the version number.
//Set exportSchema to false, so as not to keep schema version history backups.
@Database(entities = [Tweet::class], version = 2, exportSchema = false)
abstract class TwitterDatabase: RoomDatabase() {

    abstract val tweetDao: TweetDao

    companion object {
        //        The value of a volatile variable will never be cached, and all writes and reads
//        will be done to and from the main memory. This helps make sure the value of INSTANCE
//        is always up-to-date and the same to all execution threads. It means that changes made by
//        one thread to INSTANCE are visible to all other threads immediately, and
//        you don't get a situation where, say, two threads each update the same entity in a cache,
//        which would create a problem.
        @Volatile
        private var INSTANCE: TwitterDatabase? = null

        fun getInstance(context: Context): TwitterDatabase {
            synchronized(this) {
                //copy the current value of INSTANCE to a local variable instance
                var instance = INSTANCE
                if(instance == null) {
                    //                    if instance is null, use the database builder to get a database.
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TwitterDatabase::class.java,
                        "twitter_database"
                    )
                        .addMigrations(MIGRATION_1_2)
//                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE \"Tweet\" ADD COLUMN \"url\" TEXT")
            }
        }
    }
}