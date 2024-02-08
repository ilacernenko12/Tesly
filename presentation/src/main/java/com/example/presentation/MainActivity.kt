package com.example.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.ui.rates.AllRatesFragment
import com.example.presentation.util.gone
import com.example.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.vBtnCurrencyRates.setOnClickListener {
            hideActivityElements()

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AllRatesFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun hideActivityElements() {
        with(binding) {
            gone(vTvAppPreview)
            gone(vBtnCurrencyRates)
            gone(vBtnCurrencyCart)
            gone(vBtnCurrencyChart)
        }
    }

    fun showActivityElements() {
        with(binding) {
            visible(vTvAppPreview)
            visible(vBtnCurrencyRates)
            visible(vBtnCurrencyCart)
            visible(vBtnCurrencyChart)
        }
    }
}