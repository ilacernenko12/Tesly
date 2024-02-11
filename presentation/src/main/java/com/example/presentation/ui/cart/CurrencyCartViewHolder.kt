package com.example.presentation.ui.cart

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.presentation.R
import com.example.presentation.databinding.CartItemBinding
import com.example.presentation.model.CartUiData

class CurrencyCartViewHolder(private val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(data: CartUiData) {
        with(binding) {
            vTvRateName.text = "${data.scale} ${data.rateName}"
            vTvOfficialRate.text = data.officialRate
            vTvDecemberDiff.text = data.decemberDiff
            vTvYesterdayDiff.text = data.yesterdayDiff

            // Устанавливаем иконку для разницы в декабре в зависимости от типа разницы
            if (data.isPositiveDecemberDiff) {
               loadImage(R.drawable.ic_strength_up, vIvDecemverStrength)
            } else {
                loadImage(R.drawable.ic_strength_down, vIvDecemverStrength)
            }

            // Аналогично для разницы вчерашнего дня
            if (data.isPositiveYesterdayDiff) {
                loadImage(R.drawable.ic_strength_up, vIvYesterdayStrength)
            } else {
                loadImage(R.drawable.ic_strength_down, vIvYesterdayStrength)
            }
        }
    }

    private fun loadImage(image: Int, imageView: ImageView) {
        Glide.with(itemView.context)
            .load(image)
            .override(30,30)
            .into(imageView)
    }
}