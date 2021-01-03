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

class TweetsFragmentWithDiffUtil : Fragment() {

    private lateinit var listAdapter : TweetsAdapterWithDiffUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tweets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerViewTweets = view.findViewById<RecyclerView>(R.id.recyclerViewTweets)

        val tweetDataListMutable = mutableListOf<TweetData>()
        tweetDataListMutable.add(TweetData("Zero List Item", R.drawable.image1, R.string.string1))
        tweetDataListMutable.add(TweetData("First List Item", R.drawable.image2, R.string.string2))
        tweetDataListMutable.add(TweetData("Second List Item", R.drawable.image3, R.string.string3))


        listAdapter = TweetsAdapterWithDiffUtil(object : TweetListItemClickListenerWithDiffUtil {
            override fun listItemClickListener(tweetData: TweetData) {
                Toast.makeText(activity, "${tweetData.tweetText} clicked", Toast.LENGTH_SHORT).show()
                view.findNavController().navigate(R.id.action_tweetsFragmentWithDiffUtil_to_tweetDetailsFragment)
            }

            override fun textViewListItemClickListener(textString: String) {
                Toast.makeText(activity, "$textString clicked", Toast.LENGTH_SHORT).show()
            }

            override fun imageViewListItemClickListener() {
                Toast.makeText(activity, "Image view clicked", Toast.LENGTH_SHORT).show()
            }
        })
        listAdapter.submitList(tweetDataListMutable)
        recyclerViewTweets.adapter = listAdapter






//        val layoutManagerCustom = GridLayoutManager(activity, 3, GridLayoutManager.HORIZONTAL, false)
        val layoutManagerCustom = LinearLayoutManager(activity)
        recyclerViewTweets.layoutManager = layoutManagerCustom




        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonAdd).setOnClickListener {
            tweetDataListMutable.add(TweetData(Random.nextInt().toString(), R.drawable.image5, R.string.string5))
            listAdapter.submitList(ArrayList(tweetDataListMutable))
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonDelete).setOnClickListener {
            tweetDataListMutable.removeLast()
            recyclerViewTweets.adapter?.notifyDataSetChanged()
        }
    }
}