package com.kalan.android.twitterclonefragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class StartFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.buttonCreateAccount).setOnClickListener {
//            startActivity(Intent(this, CreateAccount::class.java))
            view.findNavController().navigate(R.id.action_startFragment_to_createAccountFragment)
        }

        view.findViewById<Button>(R.id.buttonLogin).setOnClickListener {
//            startActivity(Intent(this, CreateAccount::class.java))
            view.findNavController().navigate(R.id.action_startFragment_to_loginFragment)
        }
    }
}