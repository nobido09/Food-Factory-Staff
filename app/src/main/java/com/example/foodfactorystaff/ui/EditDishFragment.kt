package com.example.foodfactorystaff.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.foodfactorystaff.R
import com.example.foodfactorystaff.databinding.FragmentEditDishBinding
import com.example.foodfactorystaff.model.Dish
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class EditDishFragment : Fragment() {
    private var _binding: FragmentEditDishBinding? = null
    private val binding get() = _binding!!
    private val TAG: String = "MEH"
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var dish: Dish

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditDishBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        db = Firebase.firestore
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = EditDishFragmentArgs.fromBundle(requireArguments())
        val ref = db.collection("Menu-Collection").document(args.category.lowercase()).collection("items")
        ref.get().addOnSuccessListener { docs ->
                if(docs.size()>0){
                    for (doc in docs) {
                        if (doc.id == args.id) {
                            dish = doc.toObject<Dish>()
                            binding.textprice.setText(dish.price.toString())
                            binding.textDish.setText(dish.name.toString())
                            binding.switch1.isChecked = dish.avail ?: false
                            Glide.with(this)
                                .load(dish.image.toString())
                                .placeholder(R.drawable.chef)
                                .into(binding.imgEdit)
                            when {
                                dish.veg == true -> {
                                    Glide.with(this)
                                        .load(R.drawable.veg)
                                        .into(binding.vegEdit)
                                }
                                else -> {
                                    Glide.with(this)
                                        .load(R.drawable.nonveg)
                                        .into(binding.vegEdit)
                                }
                            }
                        }
                    }
                }else{
                    Snackbar.make(binding.root, "no items found", Snackbar.LENGTH_LONG).show()
                }
            }
        binding.btnSave.setOnClickListener {
            val name = binding.textDish.text.toString()
            val price = binding.textprice.text.toString().toInt()
            val avail = binding.switch1.isChecked
            dish.name = name
            dish.price = price
            dish.avail = avail
            val document = db.collection("Menu-Collection")
                .document(args.category.lowercase())
                .collection("items")
                .document(args.id)
            document.set(dish).addOnSuccessListener {
                Snackbar.make(binding.root, "dish updated", Snackbar.LENGTH_LONG).show()
            }
        }
    }
}