package com.andela.voluminotesapp.activities;

import android.os.Build;
import android.view.View;

import com.andela.voluminotesapp.BuildConfig;
import com.andela.voluminotesapp.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

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
    public void testOnBackPressed() throws Exception {
        
    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {

    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {

    }

    @Test
    public void testOnNavigationItemSelected() throws Exception {

    }

    @Test
    public void testOnViewDrag() throws Exception {

    }

    @Test
    public void testOnNoteTouch() throws Exception {

    }

    @Test
    public void testOnNoteClick() throws Exception {

    }

    @Test
    public void testOnNoteDelete() throws Exception {

    }

    @Test
    public void testOnClick() throws Exception {

    }

    @Test
    public void testOnSaveInstanceState() throws Exception {

    }

    @Test
    public void testOnPause() throws Exception {

    }
}