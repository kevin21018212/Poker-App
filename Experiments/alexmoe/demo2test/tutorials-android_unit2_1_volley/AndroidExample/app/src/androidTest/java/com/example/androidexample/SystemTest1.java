package com.example.androidexample;

import com.example.androidexample.ViewStatsActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
//import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;
//import androidx.test.espresso.intent.rule.IntentsTestRule;
import android.content.Intent;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time

public class SystemTest1 {



    private static final int SIMULATED_DELAY_MS = 500;



    @Rule
    public ActivityScenarioRule<ViewStatsActivity> activityRule = new ActivityScenarioRule<>(ViewStatsActivity.class);

    @Test
    public void reverseString(){
        String testString = "hello";
        String resultString = "olleh";
        // Type in testString and send request
        onView(withId(R.id.enterIDEdit))
                .perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.submitID)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Verify that volley returned the correct value
        onView(withId(R.id.returnedUser)).check(matches(withText(endsWith(resultString))));

    }


    @Test
    public void correctInput(){
        String testString = "1";
        String resultString = "1";
        // Type in testString and send request
        onView(withId(R.id.enterIDEdit))
                .perform(typeText(testString), closeSoftKeyboard());
        onView(withId(R.id.submitID)).perform(click());

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Verify that volley returned the correct value
        onView(withId(R.id.returnedUser)).check(matches(withText(endsWith(resultString))));

    }

    @Test
    public void greeterSaysHello() {
        onView(withId(R.id.enterIDEdit)).perform(typeText("1"));
        onView(withId(R.id.submitID)).perform(click());
        onView(withText("User 1")).check(matches(isDisplayed()));
    }




}
