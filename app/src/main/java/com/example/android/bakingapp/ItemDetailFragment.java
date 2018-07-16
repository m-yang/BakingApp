package com.example.android.bakingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.dummy.DummyContent;


public class ItemDetailFragment extends Fragment {

    String TAG = ItemDetailFragment.class.getSimpleName();
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


        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle.containsKey(ARG_ITEM_ID)) {
            mItem = (Recipe) bundle.getParcelable(ARG_ITEM_ID);
            Log.d(TAG, mItem.getName());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (mItem != null) {
            Log.d(TAG, "mItem != null");
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.getName());
        } 

        return rootView;
    }
}
