package com.example.presentation.ui.rates

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.databinding.RateItemBinding
import com.example.presentation.model.RateUIModel

class AllRatesViewHolder(private val binding: RateItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(rate: RateUIModel) {
        binding.apply {
            vTvCurrencyName.text = rate.name
            vTvRateToday.text = rate.officialRate
            vTvRateDifference.text = rate.difference
            vTvRateDifference.setTextColor(rate.color)

            val imageName = "flag_${rate.flag.lowercase()}"
            Glide.with(itemView.context)
                .load(itemView.context.resources.getIdentifier(imageName, "drawable", itemView.context.packageName))
                .override(70,70)
                .into(vIvCountryFlag)

        }
    }
}