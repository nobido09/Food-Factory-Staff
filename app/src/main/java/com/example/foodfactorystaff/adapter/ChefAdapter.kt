package com.example.foodfactorystaff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodfactorystaff.R
import com.example.foodfactorystaff.model.Order
import com.example.foodfactorystaff.ui.ChefClickListener
import com.example.foodfactorystaff.ui.ChefFragment

class ChefAdapter(
    val coming_orders: ArrayList<Order>,
    private val chefClickListener: ChefClickListener
): RecyclerView.Adapter<ChefAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.chef_coming_item,
            parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val order: Order = coming_orders[position]
        holder.binder(order)
        holder.btnComp.setOnClickListener {
            it.tag = order
            chefClickListener.onCellClickListener(it,holder)
        }
    }

    override fun getItemCount(): Int {
        return coming_orders.size
    }
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.findViewById(R.id.imgChefSubCategory)
        val dish_name: TextView = itemView.findViewById(R.id.textChefSubCategory)
        val orderID: TextView = itemView.findViewById(R.id.textChefOrderID)
        val uid: TextView = itemView.findViewById(R.id.textChefUserID)
        val table_no: TextView = itemView.findViewById(R.id.textChefTable)

        val qty: TextView = itemView.findViewById(R.id.txtChefQty)
        val veg: ImageView = itemView.findViewById(R.id.vegChefOrder)
        val btnComp : ImageView = itemView.findViewById(R.id.imgCompletedMark)

        fun binder(order: Order) {
            orderID.text=order.orderId
            uid.text=order.uid
            table_no.text=order.table
            dish_name.text = order.name
            qty.text = order.qty.toString()

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
