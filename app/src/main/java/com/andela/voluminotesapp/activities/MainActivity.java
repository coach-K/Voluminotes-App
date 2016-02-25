package com.andela.voluminotesapp.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andela.notelib.note.Folders;
import com.andela.notelib.note.Note;
import com.andela.notelib.note.NoteManager;
import com.andela.voluminotesapp.R;
import com.andela.voluminotesapp.callbacks.FragmentRecyclerListener;
import com.andela.voluminotesapp.callbacks.OnIntersectListener;
import com.andela.voluminotesapp.callbacks.WelcomeListener;
import com.andela.voluminotesapp.fragments.MasterFragment;
import com.andela.voluminotesapp.fragments.NoteGridFragment;
import com.andela.voluminotesapp.fragments.NoteListFragment;
import com.andela.voluminotesapp.fragments.SearchFragment;
import com.andela.voluminotesapp.fragments.TrashNoteListFragment;
import com.andela.voluminotesapp.fragments.WelcomeFragment;
import com.andela.voluminotesapp.utilities.Collision;
import com.andela.voluminotesapp.utilities.Launcher;
import com.andela.voluminotesapp.utilities.MsgBox;
import com.andela.voluminotesapp.utilities.Pages;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentRecyclerListener, WelcomeListener {
    public static final String POSITION = "position";
    public static final String TYPE = "type";
    public static final String STATE = "state";
    public static final int VIBRATE = 200;

    private Context context = MainActivity.this;
    private LinearLayout deleteArea;
    private AlertDialog alertDialog;
    private NoteListFragment noteListFragment;
    private NoteGridFragment noteGridFragment;
    private TrashNoteListFragment trashNoteListFragment;
    private SearchFragment searchFragment;
    private WelcomeFragment welcomeFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        manageToolbar();

        initializeComponents();

        initializeFragments();

        if (savedInstanceState == null) {
            initialLaunch();
        } else {
            Pages.CURRENT = savedInstanceState.getInt(STATE);
            continueLaunch();
        }
    }

    private void manageToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Typeface alwaysInMyHeart = Typeface.createFromAsset(getAssets(), getString(R.string.heart_font));
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(alwaysInMyHeart);
        setSupportActionBar(toolbar);
    }

    private void initializeComponents() {
        deleteArea = (LinearLayout) findViewById(R.id.deleteArea);
        hideDelete();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.onClick(Folders.STATIC_PAPER_NOTE);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeFragments() {
        trashNoteListFragment = new TrashNoteListFragment();
        noteListFragment = new NoteListFragment();
        noteGridFragment = new NoteGridFragment();
        searchFragment = new SearchFragment();
        welcomeFragment = new WelcomeFragment();
    }

    private void initialLaunch() {
        if (MyApplication.getNoteManager(context).getNotesSize() == 0) {
            Pages.CURRENT = Pages.WELCOME;
            welcomeFragment.setWelcomeListener(this);
            addFragment(welcomeFragment);
        } else {
            Pages.CURRENT = Pages.ALL_NOTES_GRID;
            noteGridFragment.setFragmentRecyclerListener(this);
            addFragment(noteGridFragment);
        }
    }

    private void continueLaunch() {
        switch (Pages.CURRENT) {
            case Pages.WELCOME:
                launchWelcomeFragment();
                break;
            case Pages.ALL_NOTES_GRID:
                noteGridFragment.setFragmentRecyclerListener(this);
                replaceFragment(noteGridFragment);
                break;
            case Pages.ALL_NOTES_LIST:
                noteListFragment.setFragmentRecyclerListener(this);
                replaceFragment(noteListFragment);
                break;
            case Pages.TRASH:
                trashNoteListFragment.setFragmentRecyclerListener(this);
                replaceFragment(trashNoteListFragment);
                break;
            case Pages.SEARCH:
                launchSearchFragment();
                break;
        }
    }

    private void trashNoteDialog(String message, View.OnClickListener listener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.trash_note_dialog, null);
        dialogBuilder.setView(dialogView);

        TextView messageView = (TextView) dialogView.findViewById(R.id.message);
        messageView.setText(message);
        dialogView.findViewById(R.id.yes).setOnClickListener(listener);
        dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        this.alertDialog = dialogBuilder.create();
        this.alertDialog.show();
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }

    private void launchWelcomeFragment() {
        welcomeFragment.setWelcomeListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, welcomeFragment)
                .commit();
        this.invalidateOptionsMenu();
    }

    private void launchSearchFragment() {
        searchFragment.setFragmentRecyclerListener(MainActivity.this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, searchFragment)
                .commit();
    }

    private void replaceFragment(MasterFragment fragment) {
        fragment.setFragmentRecyclerListener(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
        this.invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        switch (Pages.CURRENT) {
            case Pages.WELCOME:
                setMenu(R.menu.search_menu, menu);
                break;
            case Pages.ALL_NOTES_GRID:
                setMenu(R.menu.note_grid_menu, menu);
                break;
            case Pages.ALL_NOTES_LIST:
                setMenu(R.menu.note_list_menu, menu);
                break;
            case Pages.TRASH:
                setMenu(R.menu.trash_menu, menu);
                break;
        }
        return true;
    }

    private void setMenu(int id, Menu menu) {
        getMenuInflater().inflate(id, menu);
        setSearch(menu);
    }

    private void setSearch(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.searchView);
        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Pages.PREVIOUS = Pages.CURRENT;
                launchSearchFragment();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Pages.CURRENT = Pages.PREVIOUS;
                continueLaunch();
                return true;
            }
        });
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchFragment.onQueryTextChange(newText);
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_icon:
                launchScreen(Pages.ALL_NOTES_LIST);
                break;
            case R.id.grid_icon:
                launchScreen(Pages.ALL_NOTES_GRID);
                break;
            case R.id.trashItem:
                if (!(MyApplication.getNoteManager(context).isTrashNotesEmpty())) {
                    trashNoteDialog(getString(R.string.empty_message), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            trashNoteListFragment.emptyTrash();
                            alertDialog.dismiss();
                        }
                    });
                } else {
                    MsgBox.show(context, getString(R.string.trash_empty));
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void launchScreen(int page) {
        if (page != Pages.CURRENT) {
            Pages.CURRENT = page;
            continueLaunch();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                launchScreen(Pages.WELCOME);
                break;
            case R.id.nav_allNotes:
                launchScreen(Pages.ALL_NOTES_GRID);
                break;
            case R.id.nav_trash:
                launchScreen(Pages.TRASH);
                break;
            case R.id.nav_settings:
                Launcher.launch(context, SettingsActivity.class);
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onViewDrag(RecyclerView.ViewHolder viewHolder, final int fromPosition) {
        new Collision(viewHolder.itemView, deleteArea).intersect(new OnIntersectListener() {
            @Override
            public void onIntersect() {
                if (fromPosition != -1)
                    noteGridFragment.deleteNote(fromPosition);
            }
        });
    }

    @Override
    public void onNoteTouch(int type, int position) {
        switch (type) {
            case NoteManager.NOTES:
                showDelete();
                break;
            default:
                break;
        }
    }

    @Override
    public void onNoteClick(int type, final int position, Note note) {
        switch (type) {
            case NoteManager.NOTES:
                bundleNote(POSITION, getNotePosition(note), WriteNoteActivity.class);
                break;
            case NoteManager.TRASH:
                displayTrashDialog(position);
                break;
            default:
                break;
        }
    }

    private void displayTrashDialog(final int position) {
        trashNoteDialog(getString(R.string.restore_message), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trashNoteListFragment.restoreNote(position);
                alertDialog.dismiss();
            }
        });
    }

    private int getNotePosition(Note note) {
        return MyApplication.getNoteManager(context).getNotePosition(note);
    }

    @Override
    public void onNoteDelete(int type, boolean restore, Note note) {
        switch (type) {
            case NoteManager.NOTES:
                MyApplication.getNoteManager(context).moveNoteToTrash(note);
                break;
            case NoteManager.TRASH:
                if (restore) {
                    MyApplication.getNoteManager(context).restoreNoteFromTrash(note);
                } else
                    MyApplication.getNoteManager(context).deleteFromTrash(note);
                break;
            default:
                break;
        }
        hideDelete();
    }

    private void bundleNote(String key, int value, Class<?> activity) {
        Bundle bundle = new Bundle();
        bundle.putInt(key, value);
        Launcher.launchActivity(context, bundle, activity);
    }

    private void showDelete() {
        deleteArea.setVisibility(View.VISIBLE);
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if (vibrator.hasVibrator())
            vibrator.vibrate(VIBRATE);
    }

    private void hideDelete() {
        deleteArea.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(int position) {
        switch (position) {
            case Pages.TAKE_A_NOTE:
                bundleNote(TYPE, position, WriteNoteActivity.class);
                break;
            case Pages.VIEW_ALL_NOTES:
                Pages.CURRENT = Pages.ALL_NOTES_GRID;
                continueLaunch();
                break;
            case Pages.VIEW_TRASH:
                Pages.CURRENT = Pages.TRASH;
                continueLaunch();
                break;
            case Pages.VIEW_SETTINGS:
                Launcher.launch(context, SettingsActivity.class);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE, Pages.CURRENT);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        MyApplication.getNoteManager(getBaseContext()).saveChanges();
        super.onPause();
    }
}
