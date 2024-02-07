package com.example.presentation.ui

import android.view.ViewTreeObserver
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.RateItemBinding
import com.example.presentation.model.RateUIModel

class AllRatesViewHolder(private val binding: RateItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(rate: RateUIModel) {
        binding.apply {
            vTvCurrencyName.text = rate.name
            vTvRateToday.text = rate.officialRate

            vTvCurrencyName.post {
                val height = vTvCurrencyName.height
                vTvRateToday.layoutParams.height = height
                vTvRatePrevios.layoutParams.height = height
            }
        }
    }
}