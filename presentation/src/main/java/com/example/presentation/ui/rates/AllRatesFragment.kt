package com.example.presentation.ui.rates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.R
import com.example.presentation.databinding.FragmentAllRatesBinding
import com.example.presentation.model.TableUIData
import com.example.presentation.ui.base.BaseFragment
import com.example.presentation.util.UIState
import com.example.presentation.util.dateStringStartOfDay
import com.example.presentation.util.gone
import com.example.presentation.util.setOnSafeClickListener
import com.example.presentation.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class AllRatesFragment : BaseFragment<FragmentAllRatesBinding>() {

    private val viewModel: AllRatesViewModel by viewModels()
    private val adapter = AllRatesAdapter()

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

    // логика - слать запрос в сеть один раз, дальше брать из кэша
    override fun onResume() {
        super.onResume()
        viewModel.checkCacheAndRefresh()
    }

    private fun setListeners() {
        with(binding) {
            // для возвращения на активити
            vBtnBack.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }

            // Устанавливаем слушатель кликов на кнопку
            vBtnUpdate.setOnSafeClickListener {
                updateCurrentDate()
                viewModel.checkCacheAndRefresh()
            }
        }
    }

    private fun initAdapter() {
        // Устанавливаем LayoutManager и адаптер для RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    // обновляем UI исходя из state запроса в сеть/БД
    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UIState.Loading -> showLoading()
                    is UIState.Success -> render(state.rates as List<TableUIData>)
                    is UIState.Error -> showError()
                    else -> {}
                }
            }
        }
    }

    private fun render(rates: List<TableUIData>) {
        visible(binding.vPbLoading)
        gone(binding.recyclerView)

        // Обновляем адаптер с полученными данными
        adapter.updateData(rates)

        gone(binding.vPbLoading)
        visible(binding.recyclerView)
    }

    // Функция обновления даты
    private fun updateCurrentDate() {
        binding.vTvLayoutDescription.text =
            getString(R.string.all_rates_description, dateStringStartOfDay(Date()))
    }

    override fun getErrorView(): View = binding.fragmentAllRates

    override fun getLoadingView(): View = binding.vPbLoading
    override fun getRecyclerView(): View = binding.recyclerView

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAllRatesBinding =
        FragmentAllRatesBinding.inflate(inflater, container, false)
}