package com.example.presentation.ui.cart

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentCurrencyCartBinding
import com.example.presentation.model.CartUiData
import com.example.presentation.ui.base.BaseFragment
import com.example.presentation.util.UIState
import com.example.presentation.util.dateStringStartOfDay
import com.example.presentation.util.gone
import com.example.presentation.util.setOnSafeClickListener
import com.example.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint
import getDateFromSharedPreferences
import kotlinx.coroutines.launch
import saveDateToSharedPreferences
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class CurrencyCartFragment : BaseFragment<FragmentCurrencyCartBinding>() {

    private val viewModel: CurrencyCartViewModel by viewModels()
    private val adapter = CurrencyCartAdapter()
    private var date = Date()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // отобразим текущую дату
        updateCurrentDate()

        // инициализируем адаптер
        initAdapter()

        // подписка на UIState - обновление данных приходит сюда + обработка ошибок и загрузка
        observeUiState()

        // обработчики нажатий
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        binding.vTvDate.text =
            dateStringStartOfDay(requireContext().getDateFromSharedPreferences())
        viewModel.checkCacheAndRefresh(date)
    }

    // обновляем UI исходя из state запроса в сеть/БД
    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UIState.Loading -> showLoading()
                    is UIState.Success -> render(state.rates as List<CartUiData>)
                    is UIState.Error -> showError()
                    else -> {}
                }
            }
        }
    }

    private fun render(rates: List<CartUiData>) {
        visible(binding.vPbLoading)
        gone(binding.vVgTableHeader)
        gone(binding.recyclerView)

        // Обновляем адаптер с полученными данными
        adapter.updateData(rates)

        gone(binding.vPbLoading)
        visible(binding.vVgTableHeader)
        visible(binding.recyclerView)
    }

    private fun setListeners() {
        with(binding) {
            vBtnBack.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }

            vVgCalendarLayout.setOnClickListener {
                showDatePickerDialog()
            }

            vBtnUpdate.setOnSafeClickListener {
                viewModel.loadData(date)
            }
        }
    }

    private fun initAdapter() {
        // Устанавливаем LayoutManager и адаптер для RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun updateCurrentDate() {
        binding.vTvLayoutDescription.text =
            getString(R.string.all_rates_description, dateStringStartOfDay(Date()))
    }

    private fun showDatePickerDialog() {
        // Получение текущей даты
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        // Создание DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, dayOfMonth: Int ->
                // Формирование выбранной даты
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, dayOfMonth)
                // Установка выбранной даты в TextView
                binding.vTvDate.text = dateStringStartOfDay(selectedDate.time).replace('/', '.')
                date = selectedDate.time
                requireContext().saveDateToSharedPreferences(date)
            },
            year,
            month,
            dayOfMonth
        )

        // Отображение DatePickerDialog
        datePickerDialog.show()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCurrencyCartBinding =
        FragmentCurrencyCartBinding.inflate(inflater, container, false)

    override fun getErrorView(): View = binding.fragmentCurrencyCart

    override fun getLoadingView(): View = binding.vPbLoading

    override fun getRecyclerView(): View = binding.recyclerView
}