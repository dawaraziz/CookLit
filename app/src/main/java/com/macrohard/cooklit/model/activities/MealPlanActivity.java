package com.macrohard.cooklit.model.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.ListView;

import com.macrohard.cooklit.R;

public class MealPlanActivity extends AppCompatActivity {

    CalendarView calendarView;
    ListView mealsForDayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        calendarView = findViewById(R.id.mealPlanCalendar);
        mealsForDayList = findViewById(R.id.mealsForDay);

        calendarView.setDate(System.currentTimeMillis());







    }

}
