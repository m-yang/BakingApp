package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.android.bakingapp.View.ItemListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class ListActivityBasicTest {
    @Rule public ActivityTestRule<ItemListActivity> mActivityTestRule = new ActivityTestRule<>(ItemListActivity.class);

    @Test
    public void clickRecipe_ShowsNewFragment() {
        onView(withId(R.id.item_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.item_detail_container)).check(matches(isDisplayed()));
    }

    @Test
    public void clickRecipeIngredients_ShowsRecyclerView() {
        onView(withId(R.id.item_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_ingredients_tv)).perform(click());
        onView(withId(R.id.recipe_ingredients_rv)).check(matches(isDisplayed()));
    }

    @Test
    public void clickNextStep_ShowsExoPlayer() {
        onView(withId(R.id.item_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_detail_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.exo_play)).check(matches(isDisplayed()));
    }

    @Test
    public void clickNextStep_DoesNotShowExoPlayer() {
        onView(withId(R.id.item_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_detail_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.next_step_btn)).perform(click());
        onView(withId(R.id.exo_play)).check(matches(not((isDisplayed()))));

    }

    @Test
    public void clickNextAndPrev_ShowsIntro() {
        onView(withId(R.id.item_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recipe_detail_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.next_step_btn)).perform(click());
        onView(withId(R.id.prev_step_btn)).perform(click());
        onView(withId(R.id.step_description)).check(matches(withText("Recipe Introduction")));

    }
}
