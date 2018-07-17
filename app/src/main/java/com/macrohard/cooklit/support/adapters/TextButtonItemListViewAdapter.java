package com.macrohard.cooklit.support.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.model.Recipe;

import java.util.ArrayList;

public class TextButtonItemListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> recipes;
    private ArrayList<Button> buttons;

    public TextButtonItemListViewAdapter(Context context, int resource, ArrayList<String> recipes, ArrayList<Button> buttons) {
        super(context, resource, recipes);
        this.context = context;
        this.recipes = recipes;
        this.buttons = buttons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mealplan_schedule_view, parent, false);
        }

        // Lookup view for data population



        // Populate the data into the template view using the data object
        if (position <= recipes.size() - 1) {
            initializeRecipeName(convertView,position);
            initializeDeleteButton(convertView,position);

        }

        return convertView;

    }


    public void initializeRecipeName(View view,int position){
        TextView recipeName = view.findViewById(R.id.savedRecipeNameText);

        recipeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void initializeDeleteButton(View view, int position){
        Button deleteSavedRecipeButton = view.findViewById(R.id.deleteSavedRecipeButton);
        deleteSavedRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO::Sean: Need the query to delete recipe by id
            }
        });
    }



}
