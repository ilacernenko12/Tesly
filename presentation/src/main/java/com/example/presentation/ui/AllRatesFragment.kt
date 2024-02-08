package com.example.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.MainActivity
import com.example.presentation.R
import com.example.presentation.databinding.FragmentAllRatesBinding
import com.example.presentation.model.RateUIModel
import com.example.presentation.util.UIState
import com.example.presentation.util.gone
import com.example.presentation.util.setOnSafeClickListener
import com.example.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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
        binding.vBtnUpdate.setOnSafeClickListener {
            updateCurrentDate()
            observeRatesData()
        }
    }
    private fun initAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeRatesData() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UIState.Loading -> showLoading()
                    is UIState.Success -> showRates(state.rates)
                    is UIState.Error -> showError()
                    else -> {}
                }
            }
        }
    }

    private fun showLoading() {
        visible(binding.vPbLoading)
        gone(binding.recyclerView)
    }

    private fun showRates(rates: List<RateUIModel>) {
        // Скрываем прогресс-бар или другой индикатор загрузки
        gone(binding.vPbLoading)
        visible(binding.recyclerView)
        // Обновляем адаптер с полученными данными
        adapter.updateRates(rates)
    }


    private fun showError() {
        // Показываем сообщение об ошибке или экран заглушку
        // Здесь можно использовать Snackbar, Toast или другие средства
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