package com.example.budgettracker.chart;

import android.graphics.Color;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.ui.ChartScroller;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.ScaleStackMode;

import java.util.List;

public class BudgetBarChart {

    private Set set;

    public BudgetBarChart(AnyChartView anyChartView) {
        Cartesian cartesian = AnyChart.cartesian();
//        cartesian.background().enabled(true).cornerType();
        cartesian.animation(true);

        cartesian.title("Incomes and expenses per month");

        cartesian.yScale().stackMode(ScaleStackMode.VALUE);
        cartesian.yAxis(0).labels().format(
                "function() {\n" +
                        "    return this.value.toLocaleString();\n" +
                        "  }");

        ChartScroller chartScroller = cartesian.xScroller();
        chartScroller.allowRangeChange();

        this.set = Set.instantiate();
//        this.set.data(entries);
        Mapping column1Data = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping column2Data = set.mapAs("{ x: 'x', value: 'value3' }");

        cartesian.column(column1Data);
        cartesian.crosshair(true);
//        cartesian.legend().enabled(true);

//        Line line = cartesian.line(lineData);
//        line.yScale(scalesLinear);

        cartesian.column(column2Data);

        com.anychart.core.axes.Linear xAxis1 = cartesian.xAxis(1d);
        xAxis1.enabled(true);
        anyChartView.setChart(cartesian);
    }

    public void setChartData(List<DataEntry> entries){
        this.set.data(entries);
    }
}
