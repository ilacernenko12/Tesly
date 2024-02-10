package com.example.presentation.ui.rates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.RateItemBinding
import com.example.presentation.model.TableUIData

class AllRatesAdapter: RecyclerView.Adapter<AllRatesViewHolder>() {
    private var data: List<TableUIData> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRatesViewHolder {
        val binding = RateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllRatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllRatesViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(newData: List<TableUIData>) {
        this.data = newData
        notifyDataSetChanged()
    }
}
