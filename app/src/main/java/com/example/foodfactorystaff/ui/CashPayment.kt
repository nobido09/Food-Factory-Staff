package com.example.foodfactorystaff.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfactorystaff.ClickListener
import com.example.foodfactorystaff.adapter.CashAdapter
import com.example.foodfactorystaff.databinding.FragmentCashPaymentBinding
import com.example.foodfactorystaff.model.Cash
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


class CashPayment : Fragment(), ClickListener {
    private val TAG: String = "MONEYY"
    private var _binding: FragmentCashPaymentBinding? = null
    private val binding get() = _binding!!
    private lateinit var cashAdapter: CashAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var cashArraylist: ArrayList<Cash>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCashPaymentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        db = FirebaseFirestore.getInstance()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewCash.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewCash.setHasFixedSize(true)
        cashArraylist = arrayListOf()
        cashAdapter = CashAdapter(this, cashArraylist, this)
        binding.recyclerViewCash.adapter = cashAdapter
        load_cash_pay()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun load_cash_pay() {

        val docRef = db.collection("payments")
        docRef.whereEqualTo("approved", "no").get().addOnSuccessListener { document ->
            if (document != null) {
                cashArraylist.clear()
                for (doc in document) {
                    cashArraylist.add(doc.toObject<Cash>())
                }
                cashAdapter.notifyDataSetChanged()
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

    override fun onCardClickListener(view: View, holder: RecyclerView.ViewHolder) {
        val pay: Cash = view.tag as Cash
        db.collection("payments").document(pay.orderId.split("/").last()).update("approved", "yes")
            .addOnSuccessListener {
                Snackbar.make(binding.root, "Successfuly updated!", Snackbar.LENGTH_LONG).show()
                load_cash_pay()
            }
    }


}