package com.example.android.bakingapp.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Model.Recipe;
import com.example.android.bakingapp.Model.Retrofit.RecipeEndpoint;
import com.example.android.bakingapp.Model.Retrofit.RetrofitClient;
import com.example.android.bakingapp.Model.StepsItem;
import com.example.android.bakingapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.android.bakingapp.Model.Retrofit.RecipeEndpoint.BASE_URL;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity implements RecipeStepAdapter.OnStepClickListener, StepDetailFragment.PrevNextListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    private boolean mTwoPane;
    private int currStep;
    Retrofit mRetrofit;

    static Recipe recipe;

    String TAG = ItemListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        ///////// Retrofit Call /////////
        mRetrofit = RetrofitClient.getInstance(BASE_URL);

        RecipeEndpoint client = mRetrofit.create(RecipeEndpoint.class);

        Call<List<Recipe>> call = client.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                List<Recipe> recipesList = response.body();

                for(Recipe r : recipesList) {
                    Log.d(TAG, r.getName());
                }

                View recyclerView = findViewById(R.id.item_list);
                assert recyclerView != null;
                setupRecyclerView((RecyclerView) recyclerView, recipesList);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG, "failed: " + t.getMessage() + t.getCause());
            }
        });
        /////////////////////////////////

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<Recipe> recipesList) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, recipesList, mTwoPane));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<Recipe> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe = (Recipe) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(ItemDetailFragment.ARG_ITEM_ID, recipe);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, recipe);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<Recipe> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getName());
            holder.mContentView.setText(mValues.get(position).getName());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}
