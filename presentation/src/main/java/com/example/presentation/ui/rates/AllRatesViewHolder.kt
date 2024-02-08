package com.example.presentation.ui.rates

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.databinding.RateItemBinding
import com.example.presentation.model.RateUIModel
import com.example.presentation.model.TableUIData

class AllRatesViewHolder(private val binding: RateItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(rate: TableUIData) {
        binding.apply {
            vTvCurrencyName.text = rate.rates.name
            vTvRateToday.text = rate.rates.officialRate
            vTvRateDifference.text = rate.rates.difference
            vTvRateDifference.setTextColor(rate.rates.color)

            Glide.with(itemView.context)
                .load(rate.flags.flagUrl)
                .override(70,70)
                .into(vIvCountryFlag)
        }
    }
}