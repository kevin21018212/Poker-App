package com.example.androidexample;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


//@RunWith(UpdateProfileActivityTest.RobolectricTestRunner.class)
@RunWith(AndroidJUnit4ClassRunner.class)
public class UpdateTests {

    @Rule
    public ActivityScenarioRule<UpdateProfileActivity> activityRule = new ActivityScenarioRule<>(UpdateProfileActivity.class);

    private UpdateProfileActivity activity;

//    public void testUpdateProfileSuccess() {
//        // Type new values into the input fields
//        Espresso.onView(ViewMatchers.withId(R.id.newUsername)).perform(ViewActions.typeText("user1"));
//        Espresso.onView(ViewMatchers.withId(R.id.newPassword)).perform(ViewActions.typeText("password1"));
//        Espresso.onView(ViewMatchers.withId(R.id.newEmail)).perform(ViewActions.typeText("newemail@example.com"));
//        Espresso.onView(ViewMatchers.withId(R.id.newId)).perform(ViewActions.typeText("1"));
//
//        // Close the soft keyboard
//        Espresso.closeSoftKeyboard();
//
//        // Perform click on the submit button
//        Espresso.onView(ViewMatchers.withId(R.id.submitBtn)).perform(ViewActions.click());
//
//        // Add assertions for the expected behavior after clicking submit
//        // For example, check if a specific UI element is displayed indicating success
//        Espresso.onView(ViewMatchers.withId(R.id.textView))
//                .check(matches(isDisplayed()));
//    }


    @Test
    public void testActivityNotNull() {
        assertNotNull(activity);
    }

    @Test
    public void testBackToLoginButtonClick() {
        Button backToLoginButton = activity.findViewById(R.id.homeBtn);
        backToLoginButton.performClick();

        assertTrue(activity.isFinishing());
    }

    @Test
    public void testSubmitButtonClick() {
        // Set up EditTexts with test values
        EditText newUsernameEditText = activity.findViewById(R.id.newUsername);
        newUsernameEditText.setText("newUsername");
        // Set up other EditTexts as needed

        Button submitButton = activity.findViewById(R.id.submitBtn);
        submitButton.performClick();

        // Assert expected behavior after submit button click
        // For example, check if a Toast message is displayed using Robolectric's ShadowToast
        //assertEquals("Updating User Info", activity.getTextOfLatestToast());
    }





}
