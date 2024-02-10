package com.example.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.databinding.ActivityMainBinding
import com.example.presentation.ui.cart.CurrencyCartFragment
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

        with(binding) {
            vBtnCurrencyRates.setOnClickListener {
                hideActivityElements()
                showFragment(AllRatesFragment())
            }

            vBtnCurrencyCart.setOnClickListener {
                hideActivityElements()
                showFragment(CurrencyCartFragment())
            }
        }
    }

    fun hideActivityElements() {
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

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}