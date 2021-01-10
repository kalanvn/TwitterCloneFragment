package com.kalan.android.twitterclonefragment.tweetdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kalan.android.twitterclonefragment.R

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
        viewModelFactory = TweetDetailsViewModelFactory(arguments?.getString("tweetName"))
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(TweetDetailsViewModel::class.java)
//        val tweetNameString = arguments?.getString("tweetName")
//        view.findViewById<TextView>(R.id.textViewContent).text = viewModel.tweetNameLiveData.value

        viewModel.tweetNameLiveData.observe(viewLifecycleOwner, Observer {
                newValue ->
            view.findViewById<TextView>(R.id.textViewContent).text = newValue
        })
    }
}