package com.kalan.android.twitterclonefragment.tweetpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kalan.android.twitterclonefragment.R
import com.kalan.android.twitterclonefragment.database.Tweet
import com.kalan.android.twitterclonefragment.database.TwitterDatabase

class TweetPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tweetpost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val twitterDatabase = TwitterDatabase.getInstance(requireNotNull(this.activity))
        val tweetDao = twitterDatabase.tweetDao
        val viewModelFactory = TweetPostViewModelFactory(tweetDao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TweetPostViewModel::class.java)

        val tweetEditText : EditText = view.findViewById(R.id.editTextTextMultiLine)
        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonPost).setOnClickListener {
            //TODO save tweet to database
            val tweetTextString : String = tweetEditText.text.toString()
            val tweet = Tweet(text = tweetTextString, postedTime = System.currentTimeMillis())
            viewModel.onPost(tweet)
            view.findNavController().navigate(R.id.action_tweetPostFragment_to_tweetsFragment)
        }
    }
}