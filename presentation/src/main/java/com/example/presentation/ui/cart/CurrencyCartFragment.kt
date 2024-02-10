package com.example.presentation.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.presentation.MainActivity
import com.example.presentation.R
import com.example.presentation.databinding.FragmentCurrencyCartBinding
import com.example.presentation.model.CartUiData
import com.example.presentation.util.UIState
import com.example.presentation.util.gone
import com.example.presentation.util.visible
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class CurrencyCartFragment : Fragment() {
    private lateinit var binding: FragmentCurrencyCartBinding
    private val viewModel: CurrencyCartViewModel by viewModels()
    private val adapter = CurrencyCartAdapter()
    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // отобразим текущую дату
        updateCurrentDate()

        // инициализируем адаптер
        initAdapter()

        // при вервом запуске всегда обновляем до текущей даты
        viewModel.updateData(Date())

        // подписка на UIState - обновление данных приходит сюда + обработка ошибок и загрузка
        observeUiState()

        // обработчики нажатий
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        // чтобы во время сворачивания приложения
        // или в меню запущенных не были видны элементы активити
        (activity as? MainActivity)?.hideActivityElements()
    }

    // при возврате на активити восстановим видимость элементов
    override fun onStop() {
        super.onStop()
        (activity as? MainActivity)?.showActivityElements()
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
        gone(binding.recyclerView)

        // Обновляем адаптер с полученными данными
        adapter.updateData(rates)

        gone(binding.vPbLoading)
        visible(binding.recyclerView)
    }

    private fun showLoading() {
        visible(binding.vPbLoading)
        gone(binding.recyclerView)
    }

    private fun setListeners() {
        binding.vBtnBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    private fun initAdapter() {
        // Устанавливаем LayoutManager и адаптер для RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun showError() {
        Snackbar.make(requireContext(), binding.fragmentCurrencyCart, "Ошибка запроса", Snackbar.LENGTH_LONG).show()
    }

    private fun updateCurrentDate() {
        binding.vTvLayoutDescription.text = getString(R.string.all_rates_description, getCurrentDate())
    }

    // Возвращают текущую дату в формате dd/mm/yyyy
    private fun getCurrentDate(): String {
        return simpleDateFormat.format(Date()).replace('/', '.')
    }
}