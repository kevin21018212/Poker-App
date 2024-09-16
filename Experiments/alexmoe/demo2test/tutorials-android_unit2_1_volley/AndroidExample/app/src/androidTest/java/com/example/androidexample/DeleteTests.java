package com.example.androidexample;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class DeleteTests {


    @Rule   // needed to launch the activity
    public ActivityScenarioRule<deleteProfileActivity> activityRule = new ActivityScenarioRule<>(deleteProfileActivity.class);



    @Test
    public void deleteUser_Success() {
        // Assuming that the user ID "1" exists for testing
        Espresso.onView(ViewMatchers.withId(R.id.enterIDEdit)).perform(ViewActions.typeText("1"));
        Espresso.onView(ViewMatchers.withId(R.id.deleteBtn)).perform(ViewActions.click());

        // Check if the success message is displayed
        Espresso.onView(ViewMatchers.withText("User Deleted"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void deleteUser_InvalidID() {
        // Enter an invalid user ID (non-integer)
        Espresso.onView(ViewMatchers.withId(R.id.enterIDEdit)).perform(ViewActions.typeText("abc"));
        Espresso.onView(ViewMatchers.withId(R.id.deleteBtn)).perform(ViewActions.click());

        // Check if the error message is displayed
        Espresso.onView(ViewMatchers.withText("Your User ID Should Be An Integer"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void navigateBackToMenu() {
        // Click the "Menu" button
        Espresso.onView(ViewMatchers.withId(R.id.menuBtn)).perform(ViewActions.click());


    }
}
