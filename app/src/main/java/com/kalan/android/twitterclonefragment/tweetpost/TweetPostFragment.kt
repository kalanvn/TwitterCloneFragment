package com.kalan.android.twitterclonefragment.tweetpost

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kalan.android.twitterclonefragment.R
import com.kalan.android.twitterclonefragment.database.Tweet
import com.kalan.android.twitterclonefragment.database.TwitterDatabase
import kotlin.random.Random

class TweetPostFragment : Fragment() {
    companion object {
        private const val TAG = "TweetPostFragment"
    }
    private lateinit var firestore : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tweetpost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = Firebase.firestore

        val twitterDatabase = TwitterDatabase.getInstance(requireNotNull(this.activity))
        val tweetDao = twitterDatabase.tweetDao
        val viewModelFactory = TweetPostViewModelFactory(tweetDao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TweetPostViewModel::class.java)

        val tweetEditText : EditText = view.findViewById(R.id.editTextTextMultiLine)
        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonPost).setOnClickListener {
            //save tweet to database
            val tweetTextString : String = tweetEditText.text.toString()
            val tweet = Tweet(text = tweetTextString, postedTime = System.currentTimeMillis())
            val tweetId = Random.nextLong(1000000)
            val tweetForFirebase = hashMapOf(
                "tweetId" to tweetId,
                "text" to tweet.text,
                "postedTime" to tweet.postedTime
            )
            firestore.collection("tweets")
                .add(tweetForFirebase)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    view.findNavController().navigate(R.id.action_tweetPostFragment_to_tweetsFragment)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    val alertBuilder = AlertDialog.Builder(requireContext())
                    alertBuilder.setTitle("Error")
                    alertBuilder.setMessage("Error adding document " +
                            e?.localizedMessage
                    )
//                    alertBuilder.setCancelable(true)
                    alertBuilder.setPositiveButton("Ok") { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }
                    alertBuilder.create().show()
                }
//            viewModel.onPost(tweet)
//            view.findNavController().navigate(R.id.action_tweetPostFragment_to_tweetsFragment)
        }
    }
}