package com.kalan.android.twitterclonefragment

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment: Fragment() {
    companion object {
        private const val TAG = "LoginFragment"
    }
    private var progressBar: ProgressBar? = null
    private lateinit var auth: FirebaseAuth
    private var emailEditText: EditText? =null
    private var passwordEditText: EditText? =null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.buttonLoginIn).setOnClickListener {
            auth = Firebase.auth
            progressBar = view.findViewById(R.id.progressBar)

            view.findViewById<Button>(R.id.buttonLoginIn).setOnClickListener {
                progressBar?.visibility = View.VISIBLE
                emailEditText = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
                passwordEditText = view.findViewById<EditText>(R.id.editTextTextPassword)
                loginIn(emailEditText?.text.toString(), passwordEditText?.text.toString())
            }

            //With RecyclerView.Adapter
//            view.findNavController().navigate(R.id.action_loginFragment_to_tweetsFragment)

            //With ListAdapter
//            view.findNavController().navigate(R.id.action_loginFragment_to_tweetsFragmentWithDiffUtil)
        }
    }

    private fun loginIn(email: String, password: String) {
        if (!validateForm(email, password)) {
            progressBar?.visibility = View.GONE
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    progressBar?.visibility = View.GONE
                    val alertBuilder = AlertDialog.Builder(requireContext())
                    alertBuilder.setTitle("Success")
                    alertBuilder.setMessage("Logged in successfully")
                    alertBuilder.setCancelable(false)
                    alertBuilder.setPositiveButton("Ok") { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                        //With RecyclerView.Adapter
                        view?.findNavController()?.navigate(R.id.action_loginFragment_to_tweetsFragment)
                    }
                    alertBuilder.create().show()
                } else {
                    // If sign in fails, display a message to the user.
                    progressBar?.visibility = View.GONE
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Sign in failed.",
                        Toast.LENGTH_SHORT).show()
                    val alertBuilder = AlertDialog.Builder(requireContext())
                    alertBuilder.setTitle("Error")
                    alertBuilder.setMessage("Sign in failed. " +
                            "${task.exception?.localizedMessage}")
//                    alertBuilder.setCancelable(true)
                    alertBuilder.setPositiveButton("Ok") { dialog: DialogInterface, which: Int ->
                        dialog.dismiss()
                    }
                    alertBuilder.create().show()
                    // ...
                }

                // ...
            }
    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = true
        if(TextUtils.isEmpty(email)){
            emailEditText?.error = "Required"
            valid = false
        }else{
            emailEditText?.error = null
        }
        if(TextUtils.isEmpty(password)){
            passwordEditText?.error = "Required"
            valid = false
        }else{
            passwordEditText?.error = null
        }
        return valid
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.login_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())|| super.onOptionsItemSelected(item)
    }
}