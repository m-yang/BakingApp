package com.example.android.bakingapp.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.android.bakingapp.View.ItemListActivity.RECIPE_KEY;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<String> ingredients;

    public ListRemoteViewsFactory(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        Set<String> ingredientSet = prefs.getStringSet(RECIPE_KEY, null);

        if(ingredientSet != null) {
            ingredients = new ArrayList<>(ingredientSet);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(ingredients == null) {
            return 0;
        } else {
            return ingredients.size();
        }
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if(ingredients == null) {
            return null;
        }

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.ingredient_item_tv,ingredients.get(i));

        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
