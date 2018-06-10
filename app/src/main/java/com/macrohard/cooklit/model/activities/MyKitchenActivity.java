package com.macrohard.cooklit.model.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.macrohard.cooklit.database.model.Ingredient;
import com.macrohard.cooklit.database.model.IngredientViewModel;
import com.macrohard.cooklit.R;
import com.macrohard.cooklit.controller.IngredientListAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyKitchenActivity extends AppCompatActivity{
    private IngredientViewModel mIngredientViewModel;
    public static final int NEW_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;
    private String[] ingrediantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_kitchen);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final IngredientListAdapter adapter = new IngredientListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mIngredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        mIngredientViewModel.getAllIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> ingredients) {
                adapter.setIngredients(ingredients);
            }
        });

        // We might want to delete it at the end.
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyKitchenActivity.this, AddItemActivity.class);
                startActivityForResult(intent, NEW_INGREDIENT_ACTIVITY_REQUEST_CODE);
            }
        });

        /// Dawar Edithing (BOTTOM)
        final Button button = (Button) findViewById(R.id.AddMoreFood);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivityForResult(intent, NEW_INGREDIENT_ACTIVITY_REQUEST_CODE);
            }

        });


        // Qian Editing (TOP)
        Button cooklit_button = (Button) findViewById(R.id.cooklit_button);


        cooklit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable()){
                Intent i2;
                i2 = new Intent(MyKitchenActivity.this, RecipeResultListActivity.class);

                Log.d("list", mIngredientViewModel.getAllIngredients().getValue().get(0).getName());
                ingrediantList = new String[mIngredientViewModel.getAllIngredients().getValue().size()];

                for(int i = 0; i < mIngredientViewModel.getAllIngredients().getValue().size();++i){
                    ingrediantList[i] = mIngredientViewModel.getAllIngredients().getValue().get(i).getName();
                }
                i2.putExtra("ingrediants",ingrediantList);
                startActivity(i2);
            }
                else{

                    Snackbar.make(recyclerView, "Internet is not available, please retry", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_INGREDIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Ingredient ingredient = new Ingredient(data.getStringExtra(AddItemActivity.NAME),
                    data.getStringExtra(AddItemActivity.QUANTITY),
                    data.getStringExtra(AddItemActivity.DATE));
            mIngredientViewModel.insert(ingredient);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_field,
                    Toast.LENGTH_LONG).show();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
