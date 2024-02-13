package com.example.presentation.ui.linechart

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.presentation.MainActivity
import com.example.presentation.R
import com.example.presentation.databinding.FragmentLineChartBinding
import com.github.chartcore.common.ChartTypes
import com.github.chartcore.common.Position
import com.github.chartcore.common.TextAlign
import com.github.chartcore.data.chart.ChartCoreModel
import com.github.chartcore.data.chart.ChartData
import com.github.chartcore.data.dataset.ChartNumberDataset
import com.github.chartcore.data.option.ChartOptions
import com.github.chartcore.data.option.elements.Elements
import com.github.chartcore.data.option.elements.Line
import com.github.chartcore.data.option.plugin.Plugin
import com.github.chartcore.data.option.plugin.Title
import com.github.chartcore.data.option.plugin.Tooltip
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LineChartFragment : Fragment() {
    private lateinit var binding: FragmentLineChartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLineChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

        showLineChart()
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

    private fun showLineChart() {
        val data = mapOf(
            "21.01" to 3.231,
            "22.01" to 3.735,
            "23.01" to 3.535,
            "24.01" to 3.433,
            "25.01" to 3.135,
            "26.01" to 3.532,
            "27.01" to 3.235
        )

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
}