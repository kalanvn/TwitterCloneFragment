package com.kalan.android.twitterclonefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.kalan.android.twitterclonefragment.model.TweetData

class TweetsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tweets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerViewTweets = view.findViewById<RecyclerView>(R.id.recyclerViewTweets)
//        val stringList = listOf<String>("My first list item", "Colombo", "Android", "Mars",
//        "LinearLayout", "FragmentLayout", "ConstraintLayout")

//        val tweetDataList = listOf<TweetData>(TweetData("My first list item", R.drawable.image1),
//                TweetData("Colombo", R.drawable.image2),
//                TweetData("Android", R.drawable.image3),
//                TweetData("Mars", R.drawable.image4),
//                TweetData("LinearLayout", R.drawable.image5),
//                TweetData("FragmentLayout", R.drawable.image6),
//                TweetData("ConstraintLayout", R.drawable.image7))

        val tweetDataList = listOf<TweetData>(TweetData("My first list item", R.drawable.image1, R.string.string1),
                TweetData("Colombo", R.drawable.image2, R.string.string2),
                TweetData("Android", R.drawable.image3, R.string.string3),
                TweetData("Mars", R.drawable.image4, R.string.string4),
                TweetData("LinearLayout", R.drawable.image5, R.string.string5),
                TweetData("FragmentLayout", R.drawable.image6, R.string.string6),
                TweetData("ConstraintLayout", R.drawable.image7, R.string.string7))

        recyclerViewTweets.adapter = TweetsAdapter(tweetDataList)
    }
//    "FragmentLayout", "ConstraintLayout", "View"
//    ,"ViewGroup", "Button", "TextView", "EditText",
//    "Sri Lanka", "United States", "China", "Singapore",
//    "India", "Pakistan"
}