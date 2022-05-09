package com.example.foodfactorystaff.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodfactorystaff.adapter.SubMenuAdapter
import com.example.foodfactorystaff.databinding.FragmentSubMenuBinding
import com.example.foodfactorystaff.model.Dish
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class SubMenuFragment : Fragment() {


    private val TAG: String = "MEH-MEH"
    private var _binding: FragmentSubMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var submenuadapter: SubMenuAdapter
    private lateinit var subMenuMap: HashMap<String, Dish>
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubMenuBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = Firebase.auth
        db = Firebase.firestore
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = SubMenuFragmentArgs.fromBundle(requireArguments())
        load_SubMenu(args.name)
        binding.menname.text = args.name
        binding.recyclerViewSubMenu.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewSubMenu.setHasFixedSize(true)
        subMenuMap = hashMapOf()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun load_SubMenu(name: String) {
        val docRef =
            db.collection("Menu-Collection").document(name.toLowerCase()).collection("items")
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                subMenuMap.clear()
                for (doc in document) {
                    try {
                        subMenuMap.put(doc.id, doc.toObject<Dish>())
                    } catch (e: Exception) {

                    }
                }
                submenuadapter = SubMenuAdapter(subMenuMap, this,name)
                binding.recyclerViewSubMenu.adapter = submenuadapter
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
}