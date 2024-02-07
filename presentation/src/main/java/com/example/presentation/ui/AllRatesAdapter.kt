package com.example.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.presentation.databinding.RateItemBinding
import com.example.presentation.model.RateUIModel

class AllRatesAdapter : ListAdapter<RateUIModel, AllRatesViewHolder>(RateDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRatesViewHolder {
        val binding = RateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllRatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllRatesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class RateDiffCallback : DiffUtil.ItemCallback<RateUIModel>() {
    override fun areItemsTheSame(oldItem: RateUIModel, newItem: RateUIModel): Boolean {
        return oldItem.officialRate == newItem.officialRate
    }

    override fun areContentsTheSame(oldItem: RateUIModel, newItem: RateUIModel): Boolean {
        return oldItem == newItem
    }
}
