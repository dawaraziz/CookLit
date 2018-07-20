package com.macrohard.cooklit.controller;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.macrohard.cooklit.R;
import com.macrohard.cooklit.database.model.Ingredient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients; // Cached copy of Ingredients
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date mToday = getToday();

    public Date getToday() {
        Date today = null;
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        String date = Integer.toString(year) + "-"+ Integer.toString(month) + "-"+Integer.toString(day);
        Log.d("today is: ", date);
        try {
            today = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return today;
    }

    public IngredientListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox ingredientSelectButton;
        private final TextView ingredientNameView;
        private final TextView ingredientDateView;


        private IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientSelectButton = itemView.findViewById(R.id.checkBox);
            ingredientNameView = itemView.findViewById(R.id.nameView);
            ingredientDateView = itemView.findViewById(R.id.expirydateView);
        }
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final IngredientViewHolder holder, int position) {
        final Ingredient current = mIngredients.get(position);
        Date expireDate = null;
        try {
            expireDate = sdf.parse(current.getExpiryDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

            holder.ingredientNameView.setText(current.getName());
            holder.ingredientDateView.setText(current.getExpiryDate());

            if (expireDate.compareTo(mToday)<0){
                holder.ingredientNameView.setTextColor(Color.RED);
                holder.ingredientDateView.setTextColor(Color.RED);
            }
            else {
                holder.ingredientNameView.setTextColor(Color.parseColor("#808080"));
                holder.ingredientDateView.setTextColor(Color.parseColor("#808080"));
            }

            holder.ingredientSelectButton.setChecked(current.getSelected());

            holder.ingredientSelectButton.setOnCheckedChangeListener(null);

            // if true, curent checkbox will be selected, else unselected
            holder.ingredientSelectButton.setChecked(current.getSelected());

            holder.ingredientSelectButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    //set the ingreident's last status
                    current.setSelected(isChecked);

                }
            });

    }

    public boolean anyChecked() {
        for (int i=0; i < mIngredients.size(); ++i){
            if (mIngredients.get(i).getSelected()){
                return true;
            }
        }
        return false;
    }

    public void setIngredients(List<Ingredient> ingredients){
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mIngredients has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mIngredients != null)
            return mIngredients.size();
        else return 0;
    }




}
