package com.macrohard.cooklit.model.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.macrohard.cooklit.R;


public class FirstTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time);

        View startCookLitButton = findViewById(R.id.button_startUpCookLit);
        startCookLitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startTour();
            }
        });
    }

//TODO::FM::Need to make this activity a one time thing

    private void startTour(){
        Intent intent = new Intent(this, MyKitchenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }



}
