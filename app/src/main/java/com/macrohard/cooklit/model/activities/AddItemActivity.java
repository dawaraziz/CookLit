package com.macrohard.cooklit.model.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;


import com.macrohard.cooklit.R;

public class AddItemActivity extends AppCompatActivity {

    public static final String NAME = "com.macrohard.cooklit.NAME";
    public static final String QUANTITY = "com.macrohard.cooklit.QUANTITY";
    public static final String DATE = "com.macrohard.cooklit.DATE";

    private EditText mEditNameView;
    private EditText mEditQuantityView;
    private DatePicker mDatePickerView;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_additem);

        mEditNameView = findViewById(R.id.name);
        mEditQuantityView = findViewById(R.id.quantity);
        mDatePickerView= findViewById(R.id.date);

        setCurrentDateOnView();

        final Button add_button = findViewById(R.id.add);


        add_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(mEditNameView.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = mEditNameView.getText().toString();
                    String quantity = mEditQuantityView.getText().toString();
                    String stryear = String.valueOf(mDatePickerView.getYear());
                    String strmonth = String.valueOf(mDatePickerView.getMonth()+1);
                    String strday = String.valueOf(mDatePickerView.getDayOfMonth());
                    String date = stryear + "-" + strmonth + "-" +strday;
                    replyIntent.putExtra(NAME, name);
                    replyIntent.putExtra(QUANTITY, quantity);
                    replyIntent.putExtra(DATE, date);
                    setResult(RESULT_OK, replyIntent);
                }

                finish();
            }
        });
    }

    // display current date
    public void setCurrentDateOnView() {

        mDatePickerView = (DatePicker) findViewById(R.id.date);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        // set current date into datepicker
        mDatePickerView.init(year, month, day, null);

    }

}
