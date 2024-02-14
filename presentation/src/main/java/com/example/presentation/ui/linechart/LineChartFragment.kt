package com.example.presentation.ui.linechart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.presentation.R
import com.example.presentation.databinding.FragmentLineChartBinding
import com.example.presentation.ui.base.BaseFragment
import com.example.presentation.util.UIState
import com.example.presentation.util.gone
import com.github.chartcore.common.ChartTypes
import com.github.chartcore.data.chart.ChartCoreModel
import com.github.chartcore.data.chart.ChartData
import com.github.chartcore.data.dataset.ChartNumberDataset
import com.github.chartcore.data.option.ChartOptions
import com.github.chartcore.data.option.elements.Elements
import com.github.chartcore.data.option.elements.Line
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LineChartFragment : BaseFragment<FragmentLineChartBinding>() {

    private val viewModel: LineChartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        observeUiState()
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is UIState.Loading -> showLoading()
                    is UIState.Success -> showLineChart(state.rates as Map<String, Double>)
                    is UIState.Error -> showError()
                    else -> {}
                }
            }
        }
    }

    private fun showLineChart(data: Map<String, Double>) {
        gone(binding.vPbLoading)
        updateHeader(data)

        val coreData = ChartData()
            .addDataset(
                ChartNumberDataset()
                    .data(data.values.toList())
                    .label("Динамика курса USD НБ РБ")
            )
            .labels(data.keys.toList())

        val chartOptions = ChartOptions()
            .elements(
                Elements()
                    .line(
                        Line()
                            .tension(0.5f)
                    )
            )

        val chartModel = ChartCoreModel()
            .type(ChartTypes.LINE)
            .data(coreData)
            .options(chartOptions)

        binding.chartCore.draw(chartModel)
    }

    private fun updateHeader(data: Map<String, Double>) {
        with(binding) {
            vTvCurrentRate.text = data.values.last().toString()
            vTvDateTime.text = data.keys.last()
            vTvSalesCurrency.text = data.values.last().toString()
        }
    }

    private fun setListeners() {
        with(binding) {
            vBtnBack.setOnClickListener {
                activity?.supportFragmentManager?.popBackStack()
            }

            vBtnWeek.setOnClickListener {
                vBtnWeek.setBackgroundResource(R.drawable.round_button_shape)
                vBtnWeek.setTextColor(resources.getColor(R.color.white))
                vBtnMonth.setBackgroundResource(R.drawable.chart_button_border)
                vBtnMonth.setTextColor(resources.getColor(R.color.black))
                vBtnQuarter.setBackgroundResource(R.drawable.chart_button_border)
                vBtnQuarter.setTextColor(resources.getColor(R.color.black))
            }

            vBtnMonth.setOnClickListener {
                vBtnMonth.setBackgroundResource(R.drawable.round_button_shape)
                vBtnMonth.setTextColor(resources.getColor(R.color.white))
                vBtnWeek.setBackgroundResource(R.drawable.chart_button_border)
                vBtnWeek.setTextColor(resources.getColor(R.color.black))
                vBtnQuarter.setBackgroundResource(R.drawable.chart_button_border)
                vBtnQuarter.setTextColor(resources.getColor(R.color.black))
            }

            vBtnQuarter.setOnClickListener {
                vBtnQuarter.setBackgroundResource(R.drawable.round_button_shape)
                vBtnQuarter.setTextColor(resources.getColor(R.color.white))
                vBtnMonth.setBackgroundResource(R.drawable.chart_button_border)
                vBtnMonth.setTextColor(resources.getColor(R.color.black))
                vBtnWeek.setBackgroundResource(R.drawable.chart_button_border)
                vBtnWeek.setTextColor(resources.getColor(R.color.black))
            }
        }
    }

    override fun getErrorView(): View = binding.fragmentLineChartBinding
    override fun getLoadingView(): View = binding.vPbLoading
    override fun getRecyclerView(): View? = null

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLineChartBinding = FragmentLineChartBinding.inflate(inflater, container, false)
}