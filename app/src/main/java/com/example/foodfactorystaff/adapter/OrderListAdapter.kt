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
import de.hdodenhof.circleimageview.CircleImageView

class OrderListAdapter(private val fragment: Fragment, val orders: ArrayList<Order>): RecyclerView.Adapter<OrderListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.order_list_item,
            parent, false)
        return OrderListAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order: Order = orders[position]
        holder.binder(order, fragment)
    }

    override fun getItemCount(): Int {
        return orders.size
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val orderID: TextView = itemView.findViewById(R.id.textChefOrderID)
        val userID: TextView = itemView.findViewById(R.id.textChefUserID)
        val table: TextView = itemView.findViewById(R.id.textChefTable)
//        val date: TextView = itemView.findViewById(R.id.textDateTime)
        val name: TextView = itemView.findViewById(R.id.textChefSubCategory)
        val price: TextView = itemView.findViewById(R.id.priceChefDish)
        val qty: TextView = itemView.findViewById(R.id.txtChefQty)
        val veg: ImageView = itemView.findViewById(R.id.vegChefOrder)
        val img: CircleImageView = itemView.findViewById(R.id.imgChefSubCategory)


        fun binder(order: Order, fragment: Fragment) {
            orderID.text = "Order ID: " +order.orderId
            userID.text = "User ID: " + order.uid
            table.text = order.table
            name.text = order.name
            price.text = "₹" +order.price.toString()
            qty.text = order.qty.toString()
            Glide.with(img)
                .load(order.image)
                .placeholder(R.drawable.chef)
                .into(img)
            if (order.veg == true)
            {
                Glide.with(veg)
                    .load(R.drawable.veg)
                    .into(veg)
            }
            else {
                Glide.with(veg)
                    .load(R.drawable.nonveg)
                    .into(veg)
            }
        }

    }
}