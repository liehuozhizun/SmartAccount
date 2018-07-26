package com.demo.account.util;

import android.graphics.Color;

import com.demo.account.App;
import com.demo.account.R;
import com.github.mikephil.charting.charts.PieChart;

public class ChartUtils {

    private ChartUtils() {
    }

    /**
     * initialize the chart diagram
     * @param chart pieChart
     */
    public static void initPieChart(PieChart chart){
        chart.setNoDataText(App.getContext().getString(R.string.empty_data));
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(true);
        chart.setDrawEntryLabels(false);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.getLegend().setEnabled(false);
    }

}
