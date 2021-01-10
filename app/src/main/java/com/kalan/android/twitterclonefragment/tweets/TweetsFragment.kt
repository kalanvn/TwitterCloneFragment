package com.kalan.android.twitterclonefragment.tweets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kalan.android.twitterclonefragment.R
import com.kalan.android.twitterclonefragment.TweetListItemClickListener
import com.kalan.android.twitterclonefragment.TweetsAdapter
import com.kalan.android.twitterclonefragment.model.TweetData
import kotlin.random.Random

class TweetsFragment : Fragment() {
    private lateinit var viewModel: TweetsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tweets, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("TweetsFragment", "TweetsViewModel onViewCreated")
//        viewModel = TweetsViewModel()
        viewModel = ViewModelProvider(this).get(TweetsViewModel::class.java) //recommended way

        val recyclerViewTweets = view.findViewById<RecyclerView>(R.id.recyclerViewTweets)
        recyclerViewTweets.adapter = TweetsAdapter(viewModel.tweetDataListMutable, object : TweetListItemClickListener {
            override fun listItemClickListener(tweetData: TweetData) {
                Toast.makeText(activity, "${tweetData.tweetText} clicked", Toast.LENGTH_SHORT).show()
                val bundle = bundleOf("tweetName" to tweetData.tweetText)
                view.findNavController().navigate(R.id.action_tweetsFragment_to_tweetDetailsFragment, bundle)
            }
            override fun textViewListItemClickListener(textString: String) {}
            override fun imageViewListItemClickListener() {}
        })

        val layoutManagerCustom = LinearLayoutManager(activity)
        recyclerViewTweets.layoutManager = layoutManagerCustom

        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonAdd).setOnClickListener {
            viewModel.tweetDataListMutable.add(TweetData(Random.nextInt().toString(), R.drawable.image5, R.string.string5))
            recyclerViewTweets.adapter?.notifyDataSetChanged()
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonDelete).setOnClickListener {
            viewModel.tweetDataListMutable.removeLast()
            recyclerViewTweets.adapter?.notifyDataSetChanged()
        }
    }
}