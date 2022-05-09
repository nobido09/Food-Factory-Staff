package com.example.foodfactorystaff

import android.view.View
import androidx.recyclerview.widget.RecyclerView

interface ClickListener {
    fun onCardClickListener(view: View, holder: RecyclerView.ViewHolder)
}