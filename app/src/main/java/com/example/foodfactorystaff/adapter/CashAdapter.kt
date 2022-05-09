package com.example.foodfactorystaff.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.foodfactorystaff.R
import com.example.foodfactorystaff.model.Cash
import com.example.foodfactorystaff.ui.CashPayment


class CashAdapter(
    private val fragment: Fragment,
    private val payments: ArrayList<Cash>,
    private val clickListener: CashPayment
) :
    RecyclerView.Adapter<CashAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.cp_item,
            parent, false
        )
        return CashAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val payment: Cash = payments[position]
        holder.binder(payment, fragment)
        holder.icon.setOnClickListener {
            clickListener.onCardClickListener(it, holder)
        }
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderID: TextView = itemView.findViewById(R.id.textChefOrderID)
        val userID: TextView = itemView.findViewById(R.id.textChefUserID)
        val amount: TextView = itemView.findViewById(R.id.textAmount)
        val paymentMethod: TextView = itemView.findViewById(R.id.textPaymentMethod)
        val date: TextView = itemView.findViewById(R.id.textDateTime)
        val icon: ImageView = itemView.findViewById(R.id.iconEdit)


        fun binder(payment: Cash, fragment: Fragment) {
            orderID.text = "Order ID: " +  payment.orderId
            userID.text =  "User ID: "+payment.uid
            amount.text = "₹" + payment.amt.toString()
            paymentMethod.text = payment.payMethod
            date.text = payment.date
            icon.tag = payment
        }

    }
}