package com.example.foodfactorystaff.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface ChefClickListener {
    fun onCellClickListener(view: View, holder: RecyclerView.ViewHolder)
}