package com.example.androidexample;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class HomePageActivityTest {


    @Rule   // needed to launch the activity
    public ActivityScenarioRule<homePageActivity> activityRule = new ActivityScenarioRule<>(homePageActivity.class);



    @Test
    public void testViewStatsButton() {
        // Launch the activity

        // activityRule.launchActivity(null);

        // Click on the view stats button
        Espresso.onView(ViewMatchers.withId(R.id.viewStats)).perform(ViewActions.click());


    }

    @Test
    public void testReturnToLoginButton() {


        // Click on the return to login button
        Espresso.onView(ViewMatchers.withId(R.id.returnToLogin)).perform(ViewActions.click());


    }

    @Test
    public void testDeleteUserButton() {
        // Launch the activity
        // activityRule.launchActivity(null);

        // Click on the delete user button
        Espresso.onView(ViewMatchers.withId(R.id.deleteUser)).perform(ViewActions.click());

        // Verify your assertions here
    }

    @Test
    public void testPlayPlayersButton() {
        // Launch the activity
        // activityRule.launchActivity(null);

        // Click on the play players button
        Espresso.onView(ViewMatchers.withId(R.id.playPlayers)).perform(ViewActions.click());


    }


}
