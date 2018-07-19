package com.macrohard.cooklit.support.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TwoTextItemListViewAdapter extends ArrayAdapter<Recipe> {

    private Context context;
    private List<Recipe> recipes;

    public TwoTextItemListViewAdapter(Context context, int resource, List<Recipe> recipes) {
        super(context, resource, recipes);
        this.context = context;
        this.recipes= recipes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.mealplan_schedule_view, parent, false);
        }

        // Lookup view for data population
        TextView text1 = convertView.findViewById(R.id.viewText1);
        TextView text2 = convertView.findViewById(R.id.viewText2);

        // Populate the data into the template view using the data object
        if (position <= recipes.size() - 1) {
            text1.setText(recipes.get(position).getName());
            text2.setText(recipes.get(position).getTime());
        }

        return convertView;

    }

}
