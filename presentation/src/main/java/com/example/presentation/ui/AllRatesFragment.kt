package com.example.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.MainActivity
import com.example.presentation.R
import com.example.presentation.databinding.FragmentAllRatesBinding
import com.example.presentation.util.gone
import com.example.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
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
        initAdapter()

        // получаем данные из вью модели
        observeRatesData()

        // обработчики нажатий
        setListeners()
    }

    // при возврате на активити восстановим видимость элементов
    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.showActivityElements()
    }

    private fun setListeners() {
        // для возвращения на активити
        binding.vBtnBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        // Устанавливаем слушатель кликов на кнопку
        binding.vBtnUpdate.setOnClickListener {
            visible(binding.vPbLoading)
            updateCurrentDate()
            observeRatesData()
        }
    }
    private fun initAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeRatesData() {
        visible(binding.vPbLoading)
        viewModel.ratesData.observe(viewLifecycleOwner) { rates ->
            gone(binding.vPbLoading)
            adapter.updateRates(rates)
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
}