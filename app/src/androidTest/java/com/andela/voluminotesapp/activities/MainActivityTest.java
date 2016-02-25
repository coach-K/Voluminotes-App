package com.andela.voluminotesapp.activities;

import android.support.test.espresso.assertion.ViewAssertions;
import android.test.ActivityInstrumentationTestCase2;

import com.andela.voluminotesapp.R;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by andela on 2/13/16.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2{

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        testOnDeleteAllNote();
    }

    private void takeANote(){
        onView(withText("Take a note")).perform(click());
        onView(withId(R.id.noteTitle)).perform(typeText("01 This is a simple title"));
        onView(withId(R.id.noteArea)).perform(typeText("This is a simple content and you're gonna love it. peace!"));
        onView(withContentDescription("Navigate up")).perform(click());
    }

    @Test
    public void testOnDeleteAllNote() {
        MyApplication.getNoteManager(getActivity()).deleteAll();
        onView(withId(R.id.toolbar_title)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testOnCreateNote() throws Exception {
        takeANote();
    }


    @Test
    public void testOnReadNote() throws Exception {
        takeANote();
        onView(withId(R.id.text)).perform(click());
    }

    @Test
    public void testOnEditNote() throws Exception {
        takeANote();
        onView(withId(R.id.text)).perform(click());
        onView(withId(R.id.noteTitle)).perform(typeText("02 This is an expensive title"));
        onView(withId(R.id.noteArea)).perform(typeText("This is an expensive content and you're gonna buy it. harmony!"));
        onView(withContentDescription("Navigate up")).perform(click());
    }

    @Test
    public void testOnDelete() throws Exception {
        takeANote();
        onView(withId(R.id.list_icon)).perform(click());
        onView(withId(R.id.text)).perform(swipeLeft());
    }
}