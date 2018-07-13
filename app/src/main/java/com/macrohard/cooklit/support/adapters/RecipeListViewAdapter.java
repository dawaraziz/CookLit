package com.macrohard.cooklit.support.adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.macrohard.cooklit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RecipeListViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private ArrayList<String> imageUri;
    private ArrayList<String> titles;

    public RecipeListViewAdapter(Context context, int resource, ArrayList<String> titleimageuri, ArrayList<String> title) {
        super(context, resource, titleimageuri);
        this.context = context;
        this.imageUri = titleimageuri;
        this.titles = title;
        Log.d("initialization", "done");
        Log.d("size", "" + imageUri.size());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.d("getting view", "" + imageUri.size());

        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.elementview, parent, false);
        }

        Log.d("position is", position + "");

        // Lookup view for data population
        ImageView icon = convertView.findViewById(R.id.thumbnail);
        TextView title =  convertView.findViewById(R.id.thumbnaildescription);
        final ImageButton likebutton = convertView.findViewById(R.id.likebutton);
        likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likebutton.setImageResource(R.drawable.redheart);
            }
        });
        // Populate the data into the template view using the data object
        if (position <= imageUri.size() - 1) {
            Log.d("title", titles.get(position));
            title.setText(titles.get(position));
            Log.d("image uri", imageUri.get(position));
            Picasso.with(context).load(imageUri.get(position)).into(icon);
            //icon.setImageDrawable(objects2.get(position));

        }

        return convertView;

    }

}
