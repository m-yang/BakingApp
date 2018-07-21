package com.example.android.bakingapp.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Model.IngredientsItem;
import com.example.android.bakingapp.R;

import java.util.List;


public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.IngredientsViewHolder> {

    private static final String TAG = RecipeIngredientsAdapter.class.getName();

    private final int mNumberItems;

    private final List<IngredientsItem> ingredientsItems;

    private final Context context;

    public RecipeIngredientsAdapter(List<IngredientsItem> ingredientsItems, Context context) {

        this.ingredientsItems = ingredientsItems;
        this.context = context;
        this.mNumberItems = ingredientsItems.size();
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.recipe_detail_item, parent, false);

        return new IngredientsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientsAdapter.IngredientsViewHolder holder, int position) {
        holder.ingredientTextView.setText(ingredientsItems.get(position).getIngredient());
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder {

        final TextView ingredientTextView;

        IngredientsViewHolder(View itemView) {

            super(itemView);

            ingredientTextView = itemView.findViewById(R.id.detail_step_tv);
        }

    }
}