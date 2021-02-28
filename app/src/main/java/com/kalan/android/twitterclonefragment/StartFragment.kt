package com.kalan.android.twitterclonefragment

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

class StartFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = view.context?.getSharedPreferences(
            "mysharedpreffile", Context.MODE_PRIVATE)

        val isRunFirstTime = sharedPref?.getBoolean("isRunFirstTime", false)
        if(isRunFirstTime == false){
            val alertBuilder = AlertDialog.Builder(requireContext())
            alertBuilder.setTitle("Welcome")
            alertBuilder.setMessage("Welcome to the app")
            alertBuilder.setCancelable(false)
            alertBuilder.setPositiveButton("Ok") { dialog: DialogInterface, which: Int ->
                dialog.dismiss()
            }
            alertBuilder.create().show()
        }
        sharedPref?.edit()?.putBoolean("isRunFirstTime", true)?.apply()

        view.findViewById<Button>(R.id.buttonCreateAccount).setOnClickListener {
//            startActivity(Intent(this, CreateAccount::class.java))
            view.findNavController().navigate(R.id.action_startFragment_to_createAccountFragment)
        }

        view.findViewById<Button>(R.id.buttonLogin).setOnClickListener {
//            startActivity(Intent(this, CreateAccount::class.java))
            view.findNavController().navigate(R.id.action_startFragment_to_loginFragment)
        }

        val adView = view.findViewById<AdView>(R.id.adViewStartFragment)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener(){
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                val temp = 5
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
                val tempFail = 6
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        }
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar?.show()
    }
}