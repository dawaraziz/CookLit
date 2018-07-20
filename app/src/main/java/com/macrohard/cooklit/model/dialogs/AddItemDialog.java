package com.macrohard.cooklit.model.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.model.Ingredient;
import com.macrohard.cooklit.database.model.IngredientViewModel;
import com.macrohard.cooklit.database.model.Recipe;
import com.macrohard.cooklit.database.model.RecipeViewModel;

import java.util.Calendar;

public class AddItemDialog extends Dialog {
    Activity a;
    public static final String NAME = "com.macrohard.cooklit.NAME";
    public static final String QUANTITY = "com.macrohard.cooklit.QUANTITY";
    public static final String DATE = "com.macrohard.cooklit.DATE";

    private EditText mEditNameView;
    private DatePicker mDatePickerView;

    private short i;
    Intent replyIntent = new Intent();

    Integer idcounter = 1;

    private int year;
    private int month;
    private int day;


    public AddItemDialog(Activity a, int theme_res_id) {
        super(a, theme_res_id);
        this.a = a;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)a.getSystemService(a.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_additem);

        mEditNameView = findViewById(R.id.name);
        mDatePickerView= findViewById(R.id.date);

        mEditNameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        setCurrentDateOnView();



        final Button add_button = findViewById(R.id.add);


        add_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                if (TextUtils.isEmpty(mEditNameView.getText())){
                    a.setResult(a.RESULT_CANCELED, replyIntent);
                } else {
                    String name = mEditNameView.getText().toString();
                    int year = mDatePickerView.getYear();
                    int month = mDatePickerView.getMonth()+1;
                    int day = mDatePickerView.getDayOfMonth();
                    String date = year + "-" + (month<10?("0"+month):(month)) + "-" +day;
                    Log.d("date", date);
                    Ingredient ingredient = new Ingredient(name, date);
                    IngredientViewModel ingredientViewModel =  ViewModelProviders.of((FragmentActivity) AddItemDialog.this.a).get(IngredientViewModel.class);
                    ingredientViewModel.insert(ingredient);

                }

                dismiss();
            }
        });
    }


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
