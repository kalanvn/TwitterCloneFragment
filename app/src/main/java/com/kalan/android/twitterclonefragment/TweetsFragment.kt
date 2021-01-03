package com.kalan.android.twitterclonefragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kalan.android.twitterclonefragment.model.TweetData
import kotlin.random.Random

class TweetsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tweets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerViewTweets = view.findViewById<RecyclerView>(R.id.recyclerViewTweets)

        val tweetDataList = listOf<TweetData>(TweetData("My first list item", R.drawable.image1, R.string.string1),
                TweetData("Colombo", R.drawable.image2, R.string.string2),
                TweetData("Android", R.drawable.image3, R.string.string3),
                TweetData("Mars", R.drawable.image4, R.string.string4),
                TweetData("LinearLayout", R.drawable.image5, R.string.string5),
                TweetData("FragmentLayout", R.drawable.image6, R.string.string6),
                TweetData("ConstraintLayout", R.drawable.image7, R.string.string7))

        val tweetDataListMutable = mutableListOf<TweetData>()
        tweetDataListMutable.add(TweetData("Zero List Item", R.drawable.image1, R.string.string1))
        tweetDataListMutable.add(TweetData("First List Item", R.drawable.image2, R.string.string2))
        tweetDataListMutable.add(TweetData("Second List Item", R.drawable.image3, R.string.string3))
        recyclerViewTweets.adapter = TweetsAdapter(tweetDataListMutable, object : TweetListItemClickListener {
            override fun listItemClickListener(tweetData: TweetData) {
                Toast.makeText(activity, "${tweetData.tweetText} clicked", Toast.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.action_tweetsFragment_to_tweetDetailsFragment)
            }

            override fun textViewListItemClickListener(textString: String) {
                Toast.makeText(activity, "$textString clicked", Toast.LENGTH_SHORT).show()
            }

            override fun imageViewListItemClickListener() {
                Toast.makeText(activity, "Image view clicked", Toast.LENGTH_SHORT).show()
            }
        })

//        val layoutManagerCustom = GridLayoutManager(activity, 3, GridLayoutManager.HORIZONTAL, false)
        val layoutManagerCustom = LinearLayoutManager(activity)
        recyclerViewTweets.layoutManager = layoutManagerCustom

        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonAdd).setOnClickListener {
            tweetDataListMutable.add(TweetData(Random.nextInt().toString(), R.drawable.image5, R.string.string5))
            recyclerViewTweets.adapter?.notifyDataSetChanged()
//            tweetDataListMutable.add(1, TweetData("Zero-First List Item", R.drawable.image1, R.string.string1))
//            recyclerViewTweets.adapter?.notifyItemChanged(1)
//            recyclerViewTweets.adapter?.notifyDataSetChanged()
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonDelete).setOnClickListener {
            tweetDataListMutable.removeLast()
            recyclerViewTweets.adapter?.notifyDataSetChanged()
        }
    }
}