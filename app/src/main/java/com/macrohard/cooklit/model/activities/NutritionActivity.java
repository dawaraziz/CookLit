package com.macrohard.cooklit.model.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.macrohard.cooklit.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NutritionActivity extends AppCompatActivity {

    LineChart lineChartChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        lineChartChart = findViewById(R.id.lineChart);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0f, 3000f));
        entries.add(new Entry(1f, 2000f));
        entries.add(new Entry(2f, 2500f));
        entries.add(new Entry(3f, 1500f));
        // gap of 2f
        entries.add(new Entry(4f, 1700f));
        entries.add(new Entry(5f, 1060f));
        //BarDataSet barDataSet = new BarDataSet(entries,"Dates");

        ArrayList<Entry> target = new ArrayList<>();
        target.add(new Entry(0f, 2000f));
        target.add(new Entry(1f, 2000f));
        target.add(new Entry(2f, 2000f));
        target.add(new Entry(3f, 2000f));
        // gap of 2f
        target.add(new Entry(4f, 2000f));
        target.add(new Entry(5f, 2000f));

        LineDataSet dataSet1 = new LineDataSet(entries, "Calories"); // add entries to dataset
        dataSet1.setCircleColor(Color.DKGRAY);
        dataSet1.setColor(Color.DKGRAY);
        LineDataSet dataSet2 = new LineDataSet(target, "Target"); // add entries to dataset
        dataSet2.setColor(Color.rgb(111,29,165));
        //dataSet.setColor(...);
        //dataSet.setValueTextColor(...); // styling, ...
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataSet1);
        dataSets.add(dataSet2);
        LineData lineData = new LineData(dataSets);
        lineChartChart.setData(lineData);
        lineChartChart.invalidate(); // refresh
        final String[] quarters = new String[] { "June 20th", "June 21st", "June 22nd", "June 23rd","June 24th","June 25th" };

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return quarters[(int) value];
            }

        };

        XAxis xAxis = lineChartChart.getXAxis();
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);
        /*BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f); // set custom bar width
        lineChartChart.setData(data);
        lineChartChart.setFitBars(true); // make the x-axis fit exactly all bars
        lineChartChart.invalidate();

        Legend l = barChart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        //l.setTypeface(...);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f); // set the space between the legend entries on the y-axis

        // set custom labels and colors
        //l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "Set1", "Set2", "Set3", "Set4", "Set5" });*/
    }
}
