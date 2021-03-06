package com.example.android.bakingapp.Model.Retrofit;

import com.example.android.bakingapp.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeEndpoint {

    String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();

}
