package com.kalan.android.twitterclonefragment.tweetpost

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.kalan.android.twitterclonefragment.R
import com.kalan.android.twitterclonefragment.database.Tweet
import com.kalan.android.twitterclonefragment.database.TwitterDatabase
import java.io.File
import kotlin.random.Random

class TweetPostFragment : Fragment() {
    companion object {
        private const val TAG = "TweetPostFragment"
    }
    private lateinit var firestore : FirebaseFirestore
    private lateinit var firebaseStorage : FirebaseStorage
    private var currentTweetId : Long? = null
    private lateinit var viewModel: TweetPostViewModel
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
        firebaseStorage = Firebase.storage

        val twitterDatabase = TwitterDatabase.getInstance(requireNotNull(this.activity))
        val tweetDao = twitterDatabase.tweetDao
        val viewModelFactory = TweetPostViewModelFactory(tweetDao)
        viewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(TweetPostViewModel::class.java)
        currentTweetId = arguments?.getLong("tweetIdFromCamera")
        if(currentTweetId==null) {
            currentTweetId = Random.nextLong(1000000)
        }
//        if(viewModel.currentTweetIdInViewModel==null){
//            viewModel.currentTweetIdInViewModel = Random.nextLong(1000000)
//        }
        val tweetEditText : EditText = view.findViewById(R.id.editTextTextMultiLine)
        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonPost).setOnClickListener {

            //Create a reference to firebase storage
            val storageRef = firebaseStorage.reference
            val tweetImageStorageReference = storageRef
                    .child("tweetphotos/" + currentTweetId.toString() + "_" + 0 + ".jpg")
            val uploadFileUri = Uri.fromFile(
                    File(requireActivity().cacheDir,
                            currentTweetId.toString() + "_" + 0 + ".jpg")
            )
            val uploadTask = tweetImageStorageReference.putFile(uploadFileUri)
            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG)
                        .show()
            }.addOnSuccessListener { taskSnapshot ->
                // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                // ...
                Toast.makeText(context, taskSnapshot.metadata.toString(), Toast.LENGTH_LONG)
                        .show()
            }

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                tweetImageStorageReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    //save tweet to database
                    val tweetTextString : String = tweetEditText.text.toString()
                    firebaseFirestoreUpload(view, tweetTextString, downloadUri)

                    //            viewModel.onPost(tweet)
//            view.findNavController().navigate(R.id.action_tweetPostFragment_to_tweetsFragment)
                } else {
                    // Handle failures
                    // ...
                }
            }
        }

        view.findViewById<FloatingActionButton>(R.id.floatingActionButtonCamera)
            .setOnClickListener {
                val bundle = bundleOf("tweetId" to /*viewModel.currentTweetIdInViewModel*/currentTweetId)
                findNavController().navigate(R.id.action_tweetPostFragment_to_cameraXFragment, bundle)
            }
    }

    override fun onStart() {
        super.onStart()
        val imageViewPhoto = view?.findViewById<ImageView>(R.id.imageViewPhoto)
        if(imageViewPhoto!=null)
            Glide.with(this)
                    .load(File(requireActivity().cacheDir,
                            /*viewModel.currentTweetIdInViewModel*/currentTweetId.toString() + "_" + 0 + ".jpg"))
                    .into(imageViewPhoto)
    }


    fun firebaseFirestoreUpload(view: View, tweetTextString: String, downloadUri: Uri?){
        val tweet = Tweet(text = tweetTextString
                , postedTime = System.currentTimeMillis()
                , url = downloadUri.toString())
//            val tweetId = Random.nextLong(1000000)
        val tweetForFirebase = hashMapOf(
                "tweetId" to currentTweetId,
                "text" to tweet.text,
                "postedTime" to tweet.postedTime,
                "url" to downloadUri.toString()
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
    }
}