package com.example.android.bakingapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.StepsItem;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.View.RecipeStepAdapter.OnStepClickListener;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity implements OnStepClickListener, StepDetailFragment.PrevNextListener {

    String TAG = ItemDetailActivity.class.getSimpleName();

    Recipe recipe;
    private int currStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            recipe = getIntent().getParcelableExtra(ItemDetailFragment.ARG_ITEM_ID);

            Bundle arguments = new Bundle();
            arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID, recipe);

            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(StepsItem item, int currStep) {
        this.currStep = currStep;

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        StepDetailFragment fragment = new StepDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(StepDetailFragment.STEP_ITEM_ID, item);
        fragment.setArguments(arguments);
        ft.replace(R.id.item_detail_container, fragment)
                .commit();
        ft.addToBackStack(null);

    }

    public void goToStep() {

        StepsItem item = recipe.getSteps().get(currStep);

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        StepDetailFragment fragment = new StepDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(StepDetailFragment.STEP_ITEM_ID, item);
        fragment.setArguments(arguments);
        ft.replace(R.id.item_detail_container, fragment)
                .commit();
        ft.addToBackStack(null);

    }

    @Override
    public void onPrevClick() {
        if(this.currStep - 1 >= 0) {
            this.currStep--;
            goToStep();
        }
    }

    @Override
    public void onNextClick() {
        if(this.currStep + 1 < recipe.getSteps().size()) {
            this.currStep++;
            goToStep();
        }
    }
}
