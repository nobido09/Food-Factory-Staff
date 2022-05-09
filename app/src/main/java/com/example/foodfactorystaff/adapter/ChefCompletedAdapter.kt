package com.example.foodfactorystaff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodfactorystaff.R
import com.example.foodfactorystaff.model.Order

class ChefCompletedAdapter(private val fragment: Fragment, val orders: ArrayList<Order>): RecyclerView.Adapter<ChefCompletedAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.chef_coming_item,
            parent, false)
        return ChefCompletedAdapter.MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order: Order = orders[position]
        holder.binder(order)
    }

    override fun getItemCount(): Int {
        return orders.size
    }


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgChefSubCategory)
        val dish_name: TextView = itemView.findViewById(R.id.textChefSubCategory)
        val orderID: TextView = itemView.findViewById(R.id.textChefOrderID)
        val uid: TextView = itemView.findViewById(R.id.textChefUserID)
        val table_no: TextView = itemView.findViewById(R.id.textChefTable)
        val price: TextView = itemView.findViewById(R.id.priceChefDish)
        val qty: TextView = itemView.findViewById(R.id.txtChefQty)
        val veg: ImageView = itemView.findViewById(R.id.vegChefOrder)

        fun binder(order: Order) {
            if (order.prepared == true ) {
                orderID.text=order.orderId
                uid.text=order.uid
                table_no.text=order.table
                dish_name.text = order.name
                qty.text = order.qty.toString()
                price.text = " ₹ "+ order.price.toString()
                Glide.with(image)
                    .load(order.image)
                    .placeholder(R.drawable.chef)
                    .into(image)
                if (order.veg) {
                    Glide.with(veg)
                        .load(R.drawable.veg)
                        .placeholder(R.drawable.veg)
                        .into(veg)
                } else {
                    Glide.with(veg)
                        .load(R.drawable.nonveg)
                        .placeholder(R.drawable.nonveg)
                        .into(veg)
                }
            }
        }
    }
}