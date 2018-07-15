package com.macrohard.cooklit.model.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.macrohard.cooklit.R;


public class FirstTimeActivity extends AppCompatActivity {

    Button startAppButton;

    public static String SHARED_PREFERENCE_APP_BASICS = "appBasics";
    public static String SHARED_PREFERENCE_FIRST_TIME = "firstTime";
    public static String SHARED_PREFERENCE_TUTORIAL_DONE = "tutorialCompleted";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences basicSharedPreference = getSharedPreferences(SHARED_PREFERENCE_APP_BASICS,MODE_PRIVATE);
        if(!basicSharedPreference.getBoolean(SHARED_PREFERENCE_FIRST_TIME,true)){
            Intent myKitchenIntent = new Intent(FirstTimeActivity.this, MyKitchenActivity.class);
            startActivity(myKitchenIntent);
            finish();
            return;
        }

        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferences(SHARED_PREFERENCE_APP_BASICS, MODE_PRIVATE).edit();
        sharedPreferencesEditor.putBoolean(SHARED_PREFERENCE_FIRST_TIME,true);
        sharedPreferencesEditor.putBoolean(SHARED_PREFERENCE_TUTORIAL_DONE, false);
        sharedPreferencesEditor.apply();

        setContentView(R.layout.activity_first_time);

        startAppButton = findViewById(R.id.button_startUpCookLit);
        startAppButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startTour();
            }
        });
    }

//TODO::FM::Need to make this activity a one time thing

    private void startTour(){
        SharedPreferences.Editor sharedPreferencesEditor = getSharedPreferences(SHARED_PREFERENCE_APP_BASICS, MODE_PRIVATE).edit();
        sharedPreferencesEditor.putBoolean(SHARED_PREFERENCE_FIRST_TIME,false);
        sharedPreferencesEditor.apply();

        Intent intent = new Intent(this, MyKitchenActivity.class);
        startActivity(intent);
    }



}
