package com.example.presentation.ui.linechart

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.presentation.databinding.FragmentLineChartBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet


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

        val yVals = ArrayList<Entry>()
        yVals.add(Entry(0f, 3.33f, "0"))
        yVals.add(Entry(1f, 3.49f, "1"))
        yVals.add(Entry(2f, 3.543f, "2"))
        yVals.add(Entry(3f, 3.535f, "3"))
        yVals.add(Entry(4f, 3.536f, "4"))
        yVals.add(Entry(5f, 3.541f, "5"))
        yVals.add(Entry(6f, 3.546f, "6"))

        val set1: LineDataSet = LineDataSet(yVals, "DataSet 1")
        set1.color = Color.rgb(	70, 130, 180)
        set1.setCircleColor(Color.rgb(	70, 130, 180))
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(false)
        set1.valueTextSize = 0f
        set1.setDrawFilled(false)

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(set1)
        val data = LineData(dataSets)

        // set data
        binding.lineChart.data = data
        binding.lineChart.description.isEnabled = false
        binding.lineChart.legend.isEnabled = false
        binding.lineChart.setPinchZoom(true)
        binding.lineChart.xAxis.enableGridDashedLine(5f, 5f, 0f)
        binding.lineChart.axisRight.enableGridDashedLine(5f, 5f, 0f)
        binding.lineChart.axisLeft.enableGridDashedLine(5f, 5f, 0f)
        //lineChart.setDrawGridBackground()
        binding.lineChart.xAxis.labelCount = 11
        binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    }
}