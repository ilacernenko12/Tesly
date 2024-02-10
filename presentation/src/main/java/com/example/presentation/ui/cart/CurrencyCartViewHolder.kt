package com.example.presentation.ui.cart

import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.databinding.CartItemBinding
import com.example.presentation.model.CartUiData

class CurrencyCartViewHolder(private val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CartUiData) {
        with(binding) {
            vTvRateName.text = "${data.scale} ${data.rateName}"
            vTvOfficialRate.text = data.officialRate
            vTvDecemberDiff.text = data.decemberDiff
            vTvYesterdayDiff.text = data.yesterdayDiff
        }
    }
}