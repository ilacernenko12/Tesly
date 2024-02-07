package com.example.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.MainActivity
import com.example.presentation.R
import com.example.presentation.databinding.FragmentAllRatesBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class AllRatesFragment : Fragment() {
    private lateinit var binding: FragmentAllRatesBinding
    private val viewModel: AllRatesViewModel by viewModels()
    private val adapter = AllRatesAdapter()
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllRatesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // отобразим текущую дату
        updateCurrentDate()
        // инициализируем адаптер
        binding.recyclerView.adapter = adapter

        // Устанавливаем слушатель кликов на кнопку
        binding.vBtnUpdate.setOnClickListener {
            updateCurrentDate()
            observeAllRates()
        }

        binding.vBtnBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        observeAllRates()
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.showActivityElements()
    }

    private fun observeAllRates() {
        viewModel.allRates.observe(viewLifecycleOwner) { rates ->
            adapter.submitList(rates)
        }
    }

    // Функция обновления даты
    private fun updateCurrentDate() {
        binding.vTvLayoutDescription.text = getString(R.string.all_rates_description, getCurrentDate())
    }

    // Текущая дата
    private fun getCurrentDate(): String {
        return simpleDateFormat.format(Date()).replace('/', '.')
    }

    // Дата вчерашнего дня
    private fun getYesterdayDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        return simpleDateFormat.format(calendar.time).replace('/', '.')
    }
}