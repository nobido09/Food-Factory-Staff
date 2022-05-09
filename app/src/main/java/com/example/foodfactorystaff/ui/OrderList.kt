package com.example.foodfactorystaff.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfactorystaff.adapter.OrderListAdapter
import com.example.foodfactorystaff.databinding.FragmentOrderListBinding
import com.example.foodfactorystaff.model.Order
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
// todo add a progress bar
class OrderList : Fragment() {
    private val TAG: String = "ORDERSSS"
    private var _binding: FragmentOrderListBinding? = null
    private val binding get() = _binding!!
    private lateinit var orderListAdapter: OrderListAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var orderArraylist: ArrayList<Order>
    private var is_loaded: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        db = Firebase.firestore

        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewOrders.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewOrders.setHasFixedSize(true)
        orderArraylist = arrayListOf()
        orderListAdapter = OrderListAdapter(this, orderArraylist)
        binding.recyclerViewOrders.adapter = orderListAdapter
        load_orders()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun load_orders() {
        val orders = arrayListOf<String>()
        try {
            db.collection("orders").get().addOnSuccessListener { document ->
                if (document != null) {
                    orderArraylist.clear()

                    for (doc in document) {
                        orderArraylist.add(doc.toObject<Order>())
                    }
                    orderListAdapter.notifyDataSetChanged()
                    binding.pbMenu.visibility = View.GONE
                    is_loaded = true
                } else {
                    Log.d(TAG, "No such document")
                    binding.pbMenu.visibility = View.GONE
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with", exception)
                binding.pbMenu.visibility = View.GONE
            }

        } catch (e: Exception) {
            is_loaded = false
            binding.pbMenu.visibility = View.GONE
        }
    }
}