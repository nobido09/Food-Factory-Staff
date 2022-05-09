package com.example.foodfactorystaff.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.foodfactorystaff.R
import com.example.foodfactorystaff.databinding.FragmentCashPaymentBinding
import com.example.foodfactorystaff.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Home : Fragment() {

    private val TAG: String = "HOMEEE"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgNextMenu.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_MenuFragment)
        }

        binding.imgNextPayment.setOnClickListener {

            findNavController().navigate(R.id.action_home2_to_cashPayment)
        }

        binding.imgNextOrder.setOnClickListener {
            findNavController().navigate(R.id.action_home2_to_orderList)
        }

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_home2_to_loginFragment)
        }
    }
}