package com.example.foodfactorystaff.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfactorystaff.R
import com.example.foodfactorystaff.adapter.ChefAdapter
import com.example.foodfactorystaff.adapter.ChefCompletedAdapter
import com.example.foodfactorystaff.databinding.FragmentChefBinding
import com.example.foodfactorystaff.model.Dish
import com.example.foodfactorystaff.model.Order
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class ChefFragment : Fragment(), ChefClickListener {

    private var _binding: FragmentChefBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = "CHEFFFF"
    private lateinit var chefComingAdapter: ChefAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var comingOrder: ArrayList<Order>
    private lateinit var order: Order


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChefBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = Firebase.firestore
        auth = Firebase.auth
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comingOrder = arrayListOf()
        binding.recyclerViewChef.layoutManager = LinearLayoutManager(requireContext())
        chefComingAdapter = ChefAdapter(comingOrder, this)
        binding.recyclerViewChef.adapter = chefComingAdapter
        load_coming_orders()
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            findNavController().navigate(R.id.action_chefFragment_to_loginFragment)
        }
    }

    fun load_coming_orders() {
        val docRef = db.collection("orders").whereEqualTo("prepared",false)
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                comingOrder.clear()
                for (doc in document) {
                    comingOrder.add(doc.toObject<Order>())
                }
                binding.recyclerViewChef.adapter?.notifyDataSetChanged()
                binding.pbMenu.visibility = View.GONE
            } else {
                Log.d(TAG, "No such document")
                binding.pbMenu.visibility = View.GONE
            }
        }.addOnFailureListener { exception ->
            Log.d(TAG, "get failed with", exception)
            binding.pbMenu.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCellClickListener(view: View, holder: RecyclerView.ViewHolder) {
        val order = view.tag as Order
        val doc ="${order.uid}:${order.orderId}:${order.name}"
        db.collection("orders").document(doc).update("prepared", true)
            .addOnSuccessListener {
                Snackbar.make(binding.root, "Payment Approved", Snackbar.LENGTH_LONG).show()
                load_coming_orders()
            }
            .addOnFailureListener {
                Snackbar.make(binding.root, "Something went wrong!!", Snackbar.LENGTH_LONG).show()
                load_coming_orders()
            }
    }
}