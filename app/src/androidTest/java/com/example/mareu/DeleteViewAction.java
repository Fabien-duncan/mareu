package com.example.mareu;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

/**
 * used to perform the delete action on an item in the recycler view of the main activity.
 * used in the instrumental tests
 */
public class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View button = view.findViewById(R.id.meetings_item_ib_delete);
        // Maybe check for null
        button.performClick();
    }
}
