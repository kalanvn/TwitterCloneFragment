package com.kalan.android.twitterclonefragment.tweetdetails

import android.os.Bundle
import android.security.keystore.KeyProperties
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kalan.android.twitterclonefragment.R
import com.kalan.android.twitterclonefragment.database.TwitterDatabase


class TweetDetailsFragment : Fragment() {
    private lateinit var viewModel: TweetDetailsViewModel
    private lateinit var viewModelFactory: TweetDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tweetdetails, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tweetDao = TwitterDatabase.getInstance(requireNotNull(this.activity)).tweetDao
        viewModelFactory = TweetDetailsViewModelFactory(arguments?.getLong("tweetId"), tweetDao)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TweetDetailsViewModel::class.java)
//        val tweetNameString = arguments?.getString("tweetName")
//        view.findViewById<TextView>(R.id.textViewContent).text = viewModel.tweetNameLiveData.value

//        viewModel.tweetNameLiveData.observe(viewLifecycleOwner, Observer {
//                newValue ->
//            view.findViewById<TextView>(R.id.textViewContent).text = newValue
//        })

        val tweetId = arguments?.getLong("tweetId")

//        viewModel.tweet.observe(viewLifecycleOwner, {
//            view.findViewById<TextView>(R.id.textViewContent).text = it.text
//        })
//
//        if(tweetId!=null){
//            viewModel.loadTweetFromDb(tweetId)
//        }

        tweetId?.let {
            viewModel.loadTweetFromDbLive(tweetId).observe(viewLifecycleOwner, {
                it?.let {
                    view.findViewById<TextView>(R.id.textViewContent).text = it.text
                }
            })
        }
    }
}