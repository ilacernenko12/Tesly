package com.example.presentation.ui.rates

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.databinding.RateItemBinding
import com.example.presentation.model.TableUIData

class AllRatesViewHolder(private val binding: RateItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(data: TableUIData) {
        binding.apply {
            vTvCurrencyName.text = data.rates.name
            vTvRateToday.text = data.rates.officialRate
            vTvRateDifference.text = data.rates.difference
            vTvRateDifference.setTextColor(data.rates.color)

            Glide.with(itemView.context)
                .load(data.flags.flagUrl)
                .override(70,70)
                .into(vIvCountryFlag)
        }
    }
}