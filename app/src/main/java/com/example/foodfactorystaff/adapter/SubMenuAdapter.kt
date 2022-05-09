package com.example.foodfactorystaff.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.foodfactorystaff.R
import com.example.foodfactorystaff.adapter.SubMenuAdapter.*
import com.example.foodfactorystaff.model.Dish
import com.example.foodfactorystaff.ui.SubMenuFragment
import com.example.foodfactorystaff.ui.SubMenuFragmentDirections

class SubMenuAdapter(
    val dishMap: HashMap<String, Dish>,
    private val fragment: Fragment,
    private val category: String
) : Adapter<MyViewHolder>() {

    val ids: ArrayList<String>
    val dishes: ArrayList<Dish>

    init {
        ids = ArrayList(dishMap.keys)
        dishes = ArrayList(dishMap.values)
        Log.d("wo", ids.size.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubMenuAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.dish_item,
            parent, false
        )
        return SubMenuAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dish: Dish = dishes[position]
        val id: String = ids[position]
        holder.subMbinder(dish)
        holder.edit.setOnClickListener {
            val dir =
                SubMenuFragmentDirections.actionSubMenuFragmentToEditDishFragment(id, category)
            fragment.findNavController().navigate(dir)
        }
    }

    override fun getItemCount() = dishMap.size
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.img)
        val dish_name: TextView = itemView.findViewById(R.id.textDish)
        val price: TextView = itemView.findViewById(R.id.price)
        val veg: ImageView = itemView.findViewById(R.id.iconVeg)
        val avail: ImageView = itemView.findViewById(R.id.iconAvailable)
        val edit: ImageView = itemView.findViewById(R.id.iconEdit)


        fun subMbinder(dish: Dish) {
            dish_name.text = dish.name
            price.text = dish.price.toString()
            when {
                dish.avail -> {
                    Glide.with(avail)
                        .load(R.drawable.ic_baseline_check_circle_outline_24)
                        .into(avail)
                }
                else -> {
                    Glide.with(avail)
                        .load(R.drawable.ic_outline_cancel_24)
                        .into(avail)
                }
            }
            Glide.with(image)
                .load(dish.image.toString())
                .placeholder(R.drawable.chef)
                .into(image)
            when {
                dish.veg -> {
                    Glide.with(veg)
                        .load(R.drawable.veg)
                        .into(veg)
                }
                else -> {
                    Glide.with(veg)
                        .load(R.drawable.nonveg)
                        .into(veg)
                }
            }
        }
    }
}