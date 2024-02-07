package com.example.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.RateItemBinding
import com.example.presentation.model.RateUIModel

class AllRatesAdapter: RecyclerView.Adapter<AllRatesViewHolder>() {
    private var rates: List<RateUIModel> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRatesViewHolder {
        val binding = RateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllRatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllRatesViewHolder, position: Int) {
        val item = rates[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    fun updateRates(newRates: List<RateUIModel>) {
        rates = newRates
        notifyDataSetChanged()
    }
}
