package com.example.foodfactorystaff.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.foodfactorystaff.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = "MEH"
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {

            email = binding.loginId.text.toString() + "@foodfactory.com"
            password = binding.loginPassword.text.toString()
            if (email.length > 16 && password.length >= 8) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener { task ->
                        updateUI(task.user, "success")
                    }
                    .addOnFailureListener { ex ->
                        updateUI(null, ex.message.toString())
                    }
            } else {
                Snackbar.make(binding.root, "Please fill in the details", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }

    fun updateUI(user: FirebaseUser?, message: String) {
        if (user != null) {
            val dir: NavDirections;
            if (user.email == "manager.desk@foodfactory.com") {
                dir = LoginFragmentDirections.actionLoginFragmentToHome2()
            } else {
                dir = LoginFragmentDirections.actionLoginFragmentToChefFragment()
            }
            findNavController().navigate(dir)
        } else {
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser, "Login to continue!")
    }
}