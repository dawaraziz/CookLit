package com.macrohard.cooklit.model.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.macrohard.cooklit.R;

public class AddToMealPlanDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog d;
    public Button yes, no;

    public AddToMealPlanDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_to_meal_plan);
//        yes = (Button) findViewById(R.id.btn_yes);
//        no = (Button) findViewById(R.id.btn_no);
//        yes.setOnClickListener(this);
//        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_yes:
//                activity.finish();
//                break;
//            case R.id.btn_no:
//                dismiss();
//                break;
//            default:
//                break;
//        }
        dismiss();
    }


}

