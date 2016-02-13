package com.andela.voluminotesapp;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.andela.voluminotesapp.activities.MainActivity;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by andela on 2/12/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest extends TestCase {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shouldBeAbleToLaunchMainActivity() {
        onView(withText("Hello")).check(ViewAssertions.matches(isDisplayed()));
    }
}