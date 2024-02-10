package com.example.presentation.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.CartItemBinding
import com.example.presentation.model.CartUiData

class CurrencyCartAdapter: RecyclerView.Adapter<CurrencyCartViewHolder>() {
    private var data: List<CartUiData> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyCartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyCartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyCartViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<CartUiData>) {
        data = newData
        notifyDataSetChanged()
    }
}