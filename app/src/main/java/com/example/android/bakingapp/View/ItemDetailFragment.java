package com.example.android.bakingapp.View;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private Recipe mItem;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = getArguments().getParcelable(ARG_ITEM_ID);
            Log.d(TAG, mItem.getName());

            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (mItem != null) {
            Log.d(TAG, "mItem != null");

            RecipeIngredientsAdapter ingredientsAdapter = new RecipeIngredientsAdapter(mItem.getIngredients(), getContext());
            recipeIngredientsRv = rootView.findViewById(R.id.recipe_ingredients_rv);
            recipeIngredientsRv.setAdapter(ingredientsAdapter);
            recipeIngredientsRv.setLayoutManager(new LinearLayoutManager(getContext()));


            RecipeDetailAdapter detailAdapter = new RecipeDetailAdapter(mItem.getSteps(), getContext());
            recipeDetailRv = rootView.findViewById(R.id.recipe_detail_rv);
            recipeDetailRv.setAdapter(detailAdapter);
            recipeDetailRv.setLayoutManager(new LinearLayoutManager(getContext()));


        }

        return rootView;
    }
}
