package com.example.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.R
import com.example.presentation.databinding.ActivityAllRatesBinding
import com.example.presentation.model.RateUIModel
import kotlinx.coroutines.flow.onEach

class AllRatesActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAllRatesBinding::bind)
    private val viewModel: AllRatesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_rates)

        val list = mutableListOf<RateUIModel>()

        viewModel.allRates.onEach {
            println(it.toString())
        }
    }
}