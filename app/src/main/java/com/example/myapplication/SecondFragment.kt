package com.example.myapplication

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.chart.*
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    lateinit var chart: LineChart
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chart = view.findViewById(R.id.textview_second)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            addEntry()
        }
        initChart()
    }

    private fun initChart() {
        chart.setDrawGridBackground(false)
        chart.setScaleEnabled(false)
        chart.isDragEnabled = false
        chart.setPinchZoom(false)
        chart.setTouchEnabled(false)


//
        var xAxis = chart.xAxis
        xAxis.disableGridDashedLine()
        xAxis.disableAxisLineDashedLine()
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.mAxisMaximum = 80f
        xAxis.axisMinimum = 28f
        xAxis.axisLineColor = resources.getColor(R.color.black)
        xAxis.setLabelCount(7, true)
//
        var yAxis = chart.axisLeft
        chart.axisRight.isEnabled = false
        yAxis.setDrawGridLines(false)
        yAxis.disableGridDashedLine()
        yAxis.disableAxisLineDashedLine()
        yAxis.mAxisMaximum = 100f
        yAxis.granularity = 20f
        yAxis.mAxisMinimum = 0f
        chart.setVisibleXRange(28f,80f)
//        chart.setVisibleYRange(0f,100f,yAxis.axisDependency)
        initChartData()
        chart.animateX(5000)
    }

    private fun addEntry() {
        var data: LineData = chart.data
        data?.let {
            var set: ILineDataSet = data.getDataSetByIndex(0)
            var entry: Entry = Entry(set.entryCount * 10f, (Math.random() * 40 + 60f).toFloat())
            data.addEntry(entry, 0)
            data.notifyDataChanged()
            chart.notifyDataSetChanged()
            chart.moveViewToX(data.entryCount.toFloat())
        }
    }

    private fun initChartData() {
        val values: ArrayList<Entry> = ArrayList<Entry>()
        val set1: LineDataSet
        // create a dataset and give it a type
        set1 = LineDataSet(values, "DataSet 1")
        set1.setDrawIcons(false)

        set1.disableDashedLine()
        // black lines and points
        set1.color = Color.BLACK
        set1.setCircleColor(Color.BLACK)
        set1.setDrawValues(false)
        // line thickness and point size
        set1.lineWidth = 1f
        set1.circleRadius = 3f

        // draw points as solid circles
        set1.setDrawCircleHole(false)

        // customize legend entry
        set1.formLineWidth = 1f
        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 15f

        // set the filled area
        set1.setDrawFilled(true)
//        set1.fillFormatter =
//            IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }

        // set color of filled area
        if (Utils.getSDKInt() >= 18) {
            // drawables only supported on api level 18 and above
            val drawable =
                context?.let { ContextCompat.getDrawable(it, R.drawable.fade_red) }
            set1.fillDrawable = drawable
        } else {
            set1.fillColor = Color.BLACK
        }
        val dataSets: ArrayList<ILineDataSet> = ArrayList<ILineDataSet>()
        dataSets.add(set1) // add the data sets

        // create a data object with the data sets
        val data = LineData(dataSets)

        // set data
        chart.data = data
    }

}