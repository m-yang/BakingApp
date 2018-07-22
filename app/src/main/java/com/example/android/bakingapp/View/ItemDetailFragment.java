package com.example.android.bakingapp.View;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.R;


public class ItemDetailFragment extends Fragment {

    String TAG = ItemDetailFragment.class.getSimpleName();

    public RecyclerView recipeDetailRv;

    public RecyclerView recipeIngredientsRv;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    static Recipe mItem;
    private TextView mRecipeIngredientsTv;
    private boolean ingredientsVisible;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ingredientsVisible = false;

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = getArguments().getParcelable(ARG_ITEM_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getName());
        }

        if (mItem != null) {
            RecipeIngredientsAdapter ingredientsAdapter = new RecipeIngredientsAdapter(mItem.getIngredients(), getContext());
            recipeIngredientsRv = rootView.findViewById(R.id.recipe_ingredients_rv);
            recipeIngredientsRv.setAdapter(ingredientsAdapter);

            recipeIngredientsRv.setLayoutManager(new LinearLayoutManager(getContext()));

            mRecipeIngredientsTv = rootView.findViewById(R.id.recipe_ingredients_tv);
            mRecipeIngredientsTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(ingredientsVisible) {
                        ingredientsVisible = false;
                        hideIngredientsRv();
                    } else {
                        ingredientsVisible = true;
                        showIngredientsRv();
                    }
                }
            });

            RecipeStepAdapter detailAdapter = new RecipeStepAdapter(mItem.getSteps(), getContext());
            recipeDetailRv = rootView.findViewById(R.id.recipe_detail_rv);
            recipeDetailRv.setAdapter(detailAdapter);
            recipeDetailRv.setLayoutManager(new LinearLayoutManager(getContext()));

        }

        return rootView;
    }

    private void showIngredientsRv() {
        recipeIngredientsRv.setVisibility(View.VISIBLE);
    }

    private void hideIngredientsRv() {
        recipeIngredientsRv.setVisibility(View.GONE);
    }

}
