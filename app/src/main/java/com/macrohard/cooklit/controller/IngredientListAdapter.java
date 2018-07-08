package com.macrohard.cooklit.controller;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients; // Cached copy of Ingredients

    public IngredientListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox ingredientSelectButton;
        private final TextView ingredientNameView;
        private final TextView ingredientQtyView;
        private final TextView ingredientDateView;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientSelectButton = itemView.findViewById(R.id.checkBox);
            ingredientNameView = itemView.findViewById(R.id.nameView);
            ingredientQtyView = itemView.findViewById(R.id.quantityView);
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
        // if (mIngredients != null) {
            final Ingredient current = mIngredients.get(position);
            holder.ingredientNameView.setText(current.getName());
            holder.ingredientQtyView.setText(current.getQuantity());
            holder.ingredientDateView.setText(current.getExpiryDate());
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


            /*
        } else {
            // Covers the case of data not being ready yet.
            holder.ingredientNameView.setText("No Ingredient");
            holder.ingredientQtyView.setText("0");
            holder.ingredientDateView.setText("1988");
        }
        */
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
