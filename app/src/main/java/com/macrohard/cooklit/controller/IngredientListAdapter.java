package com.macrohard.cooklit.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final IngredientViewHolder holder, int position) {
        // if (mIngredients != null) {
            Ingredient current = mIngredients.get(position);
            holder.ingredientNameView.setText(current.getName());
            holder.ingredientQtyView.setText(current.getQuantity());
            holder.ingredientDateView.setText(current.getExpiryDate());
            //holder.ingredientSelectButton.setChecked(current.getSelected());

        holder.ingredientSelectButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                RadioButton rb = (RadioButton) v;
                //if already checked . uncheck
                if (holder.ingredientSelectButton.isChecked()){
                    holder.ingredientSelectButton.setChecked(false);
                }
                else{
                    holder.ingredientSelectButton.setChecked(true);
                }
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


    class IngredientViewHolder extends RecyclerView.ViewHolder {

        private final RadioButton ingredientSelectButton;
        private final TextView ingredientNameView;
        private final TextView ingredientQtyView;
        private final TextView ingredientDateView;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientSelectButton = itemView.findViewById(R.id.radioButton);
            ingredientNameView = itemView.findViewById(R.id.nameView);
            ingredientQtyView = itemView.findViewById(R.id.quantityView);
            ingredientDateView = itemView.findViewById(R.id.expirydateView);
        }

    }



}
