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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TwoTextItemListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> texts1;
    private ArrayList<String> texts2;

    public TwoTextItemListViewAdapter(Context context, int resource, ArrayList<String> texts1, ArrayList<String> texts2) {
        super(context, resource, texts1);
        this.context = context;
        this.texts1 = texts1;
        this.texts2 = texts2;
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
        if (position <= texts1.size() - 1) {
            text1.setText(texts1.get(position));
            text2.setText(texts2.get(position));
        }

        return convertView;

    }

}
