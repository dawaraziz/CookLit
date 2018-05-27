package com.example.cooklit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final TextView ingredientNameView;
        private final TextView ingredientQtyView;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientNameView = itemView.findViewById(R.id.nameView);
            ingredientQtyView = itemView.findViewById(R.id.quantityView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients; // Cached copy of Ingredients
    IngredientListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        if (mIngredients != null) {
            com.example.cooklit.Ingredient current = mIngredients.get(position);
            holder.ingredientNameView.setText(current.getName());
            holder.ingredientQtyView.setText(current.getQuantity());
        } else {
            // Covers the case of data not being ready yet.
            holder.ingredientNameView.setText("No Ingredient");
            holder.ingredientNameView.setText("0");
        }
    }

    void setIngredients(List<com.example.cooklit.Ingredient> ingredients){
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