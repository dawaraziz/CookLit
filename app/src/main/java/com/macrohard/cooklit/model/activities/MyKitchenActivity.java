package com.macrohard.cooklit.model.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.macrohard.cooklit.database.model.Ingredient;
import com.macrohard.cooklit.database.model.IngredientViewModel;
import com.macrohard.cooklit.R;
import com.macrohard.cooklit.controller.IngredientListAdapter;
import com.macrohard.cooklit.database.model.Recipe;

import java.util.List;

public class MyKitchenActivity extends AppCompatActivity{
    private IngredientViewModel mIngredientViewModel;
    public static final int NEW_INGREDIENT_ACTIVITY_REQUEST_CODE = 1;
    private String[] ingredientList;
    private LiveData<List<Recipe>> mRecipes;
    private String recipeList;
    private Switch peanut,alcohol,vegetarian, lowCalories;
    private Button deleteButton, openMealPlanButton, bucketListButton, cooklitButton;
    private FloatingActionButton addItemButton;

    boolean p_sig,a_sig,v_sig,l_sig;

    private RecyclerView recyclerView;
    private IngredientListAdapter adapter;
    private CheckBox checkBoxHeader;

    private ImageView cookLitImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_kitchen);
        mapActivityItems();
        setListeners();

        SharedPreferences basicSharedPreference = getSharedPreferences(FirstTimeActivity.SHARED_PREFERENCE_APP_BASICS,MODE_PRIVATE);
        if(!basicSharedPreference.getBoolean(FirstTimeActivity.SHARED_PREFERENCE_TUTORIAL_DONE,false)){
            //TODO::Fahim::add the tutorial stuff here
        }

        /*Button nutritionButton = findViewById(R.id.nutrition);
        nutritionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2;
                i2 = new Intent(MyKitchenActivity.this, NutritionActivity.class);
                startActivity(i2);
            }
        });*/

    }

    public void mapActivityItems(){


        setContentView(R.layout.activity_my_kitchen);

        p_sig = false;
        a_sig = false;
        v_sig = false;
        l_sig = false;

        cookLitImage = (ImageView) findViewById(R.id.cooklit_image);

        addItemButton = (FloatingActionButton) findViewById(R.id.fab);
        deleteButton = findViewById(R.id.delete_button);

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new IngredientListAdapter(this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        peanut = findViewById(R.id.switch_p);
        alcohol = findViewById(R.id.switch_a);
        vegetarian = findViewById(R.id.switch_v);
        lowCalories = findViewById(R.id.lc_switch);
        openMealPlanButton = (Button) findViewById(R.id.openMealPlan);
        bucketListButton = (Button) findViewById(R.id.bucketlist_button);
        cooklitButton = (Button) findViewById(R.id.cooklit_button);

        mIngredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        mIngredientViewModel.getAllIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable List<Ingredient> ingredients) {
                adapter.setIngredients(ingredients);
            }
        });

        // Checkbox Header for select all
        checkBoxHeader = findViewById(R.id.checkBox_header);

    }

    private void setListeners(){
        lowCalories.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    l_sig = true;
                    Log.d("l","true");
                }
                else{
                    l_sig = false;
                    Log.d("l","false");
                }
            }
        });
        alcohol.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    a_sig = true;
                    Log.d("a","true");
                }
                else{
                    a_sig = false;
                    Log.d("a","false");
                }
            }
        });
        vegetarian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    v_sig = true;
                    Log.d("v","true");
                }
                else{
                    v_sig = false;
                    Log.d("v","false");
                }
            }
        });
        peanut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    p_sig = true;
                    Log.d("p","true");
                }
                else{
                    p_sig = false;
                    Log.d("p","false");
                }
            }
        });
        // Add Button

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyKitchenActivity.this, AddItemActivity.class);
                startActivityForResult(intent, NEW_INGREDIENT_ACTIVITY_REQUEST_CODE);
            }
        });

        // Delete Button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ingre_size = mIngredientViewModel.getSelectedIngredients().size();

                for (int i = 0; i<ingre_size; ++i) {
                    mIngredientViewModel.deleteIngreident(mIngredientViewModel.getSelectedIngredients().get(i));

                }
            }
        });

        openMealPlanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mealPlanIntent = new Intent(MyKitchenActivity.this, MealPlanActivity.class);
                startActivity(mealPlanIntent);
            }
        });

        bucketListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent bucketListIntent = new Intent(getApplicationContext(), BucketListActivity.class);
                startActivityForResult(bucketListIntent, NEW_INGREDIENT_ACTIVITY_REQUEST_CODE);
            }
        });

        cooklitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                letsCooklit(v);
            }
        });

        cookLitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letsCooklit(view);
            }
        });

        checkBoxHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set all check box true/false
                for (int i=0; i < mIngredientViewModel.getAllIngredients().getValue().size(); i++){
                    mIngredientViewModel.getAllIngredients().getValue().get(i).setSelected(checkBoxHeader.isChecked());
                }
                //update view
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void letsCooklit(View v){
        if(isNetworkAvailable()){
            Intent i2;
            i2 = new Intent(MyKitchenActivity.this, RecipeResultListActivity.class);
            int ingre_size = mIngredientViewModel.getSelectedIngredients().size();
            if (ingre_size==0){
                Snackbar.make(recyclerView, "Please select at least one ingredient", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                ingredientList = new String[ingre_size];
                for(int i = 0; i < ingre_size ;++i){
                    ingredientList[i] = mIngredientViewModel.getSelectedIngredients().get(i).getName();
                }
            }
            if(ingre_size != 0) {
                i2.putExtra("ingredients", ingredientList);
                i2.putExtra("v", v_sig);
                i2.putExtra("p", p_sig);
                i2.putExtra("a", a_sig);
                i2.putExtra("l",l_sig);
                startActivity(i2);
            }
        } else {
            Snackbar.make(recyclerView, "Internet is not available, please retry", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_INGREDIENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int num = data.getExtras().size();
            System.out.print(num);
            if (num > 3) {
                for (int i = 1; i <= (num/3); i++) {
                    Ingredient ingredient = new Ingredient(data.getStringExtra("name" + String.valueOf(i)),
                            data.getStringExtra("date" + String.valueOf(i)));
                    mIngredientViewModel.insert(ingredient);
                }
            } else {
                Ingredient ingredient = new Ingredient(data.getStringExtra(AddItemActivity.NAME),
                        data.getStringExtra(AddItemActivity.DATE));
                mIngredientViewModel.insert(ingredient);
            }
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
