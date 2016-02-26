package com.andela.voluminotesapp.activities;

import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.andela.voluminotesapp.BuildConfig;
import com.andela.voluminotesapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by andela on 2/23/16.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOnCreate() throws Exception {
        View view = mainActivity.findViewById(R.id.drawer_layout);
        assertNotNull(view);
    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {
        final Menu menu = shadowOf(mainActivity).getOptionsMenu();
        assertEquals(menu.findItem(R.id.searchView).getTitle(), "Search");
    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {
        assertEquals(shadowOf(mainActivity).clickMenuItem(R.id.searchView), true);
    }

    @Test
    public void testOnNavigationItemSelected() throws Exception {
        assertEquals(shadowOf(mainActivity).clickMenuItem(R.id.nav_allNotes), true);
    }

    @Test
    public void testOnNoteTouch() throws Exception {
        mainActivity.findViewById(R.id.textView).performClick();
        Intent intent = Shadows.shadowOf(mainActivity).peekNextStartedActivity();
        assertEquals(WriteNoteActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testOnNoteClick() throws Exception {
        mainActivity.findViewById(R.id.textView).performClick();
        Intent intent = Shadows.shadowOf(mainActivity).peekNextStartedActivity();
        assertEquals(WriteNoteActivity.class.getCanonicalName(), intent.getComponent().getClassName());
    }

    @Test
    public void testOnNoteDelete() throws Exception {
        assertEquals(shadowOf(mainActivity).clickMenuItem(R.id.nav_allNotes), true);
        assertEquals(shadowOf(mainActivity).clickMenuItem(R.id.list_icon), true);
    }
}