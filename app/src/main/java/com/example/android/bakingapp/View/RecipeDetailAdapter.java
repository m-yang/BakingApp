package com.example.android.bakingapp.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Model.StepsItem;
import com.example.android.bakingapp.R;

import java.util.List;


public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeViewHolder> {

    private static final String TAG = RecipeDetailAdapter.class.getName();

    private final int mNumberItems;

    private final List<StepsItem> steps;

    private final Context context;

    public RecipeDetailAdapter(List<StepsItem> steps, Context context) {

        this.steps = steps;
        this.context = context;
        this.mNumberItems = steps.size();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.recipe_detail_item, parent, false);

        return new RecipeViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailAdapter.RecipeViewHolder holder, int position) {

        holder.stepTextView.setText(steps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {

        final TextView stepTextView;

        RecipeViewHolder(View itemView) {

            super(itemView);

            stepTextView = itemView.findViewById(R.id.detail_step_tv);
        }

    }
}