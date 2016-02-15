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
import com.andela.voluminotesapp.fragments.NoteGridFragment;
import com.andela.voluminotesapp.fragments.NoteListFragment;
import com.andela.voluminotesapp.fragments.SearchFragment;
import com.andela.voluminotesapp.fragments.TrashNoteListFragment;
import com.andela.voluminotesapp.fragments.WelcomeFragment;
import com.andela.voluminotesapp.utilities.Collision;
import com.andela.voluminotesapp.utilities.Launcher;
import com.andela.voluminotesapp.utilities.MsgBox;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentRecyclerListener, WelcomeListener {
    public static final String POSITION = "position";
    public static final String TYPE = "type";
    public static final String ALL_NOTE_MODE = "allNoteMode";

    private Context context = MainActivity.this;
    private LinearLayout deleteArea;
    private AlertDialog alertDialog;
    private NoteListFragment noteListFragment;
    private NoteGridFragment noteGridFragment;
    private TrashNoteListFragment trashNoteListFragment;
    private SearchFragment searchFragment;
    private WelcomeFragment welcomeFragment;
    private Toolbar toolbar;
    private String mode = ALL_NOTE_MODE;
    private boolean toggle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        manageToolbar();

        deleteArea = (LinearLayout) findViewById(R.id.deleteArea);
        hideDelete();

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        initializeFragments();
    }

    private void initializeFragments() {
        if (noteListFragment == null && noteGridFragment == null
                && trashNoteListFragment == null && searchFragment == null) {
            noteListFragment = new NoteListFragment();
            noteListFragment.setFragmentRecyclerListener(this);
            noteGridFragment = new NoteGridFragment();
            noteGridFragment.setFragmentRecyclerListener(this);
            trashNoteListFragment = new TrashNoteListFragment();
            trashNoteListFragment.setFragmentRecyclerListener(this);
            searchFragment = new SearchFragment();
            searchFragment.setFragmentRecyclerListener(this);
            welcomeFragment = new WelcomeFragment();
            welcomeFragment.setWelcomeListener(this);

            mode = "";
            replaceFragment(welcomeFragment);
        }
    }

    private void manageToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Typeface alwaysInMyHeart = Typeface.createFromAsset(getAssets(), getString(R.string.heart_font));
        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setTypeface(alwaysInMyHeart);
        setSupportActionBar(toolbar);
    }

    private void trashNoteDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.trash_note_dialog, null);
        dialogBuilder.setView(dialogView);

        dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trashNoteListFragment.restoreNote(position);
                alertDialog.dismiss();
            }
        });
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

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
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
        getMenuInflater().inflate(R.menu.note_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchView);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

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

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mode = ALL_NOTE_MODE;
                replaceFragment(noteGridFragment);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.toggleRecycler) {
            if (mode.equals(ALL_NOTE_MODE))
                toggleLayout(item);
        } else if (id == R.id.searchView) {
            mode = "";
            replaceFragment(searchFragment);
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleLayout(MenuItem item) {
        if (toggle) {
            replaceFragment(noteGridFragment);
            item.setIcon(R.mipmap.list);
        } else {
            replaceFragment(noteListFragment);
            item.setIcon(R.mipmap.grid);
        }
        toggle = !toggle;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                mode = "";
                replaceFragment(welcomeFragment);
                break;
            case R.id.nav_allNotes:
                mode = ALL_NOTE_MODE;
                replaceFragment(noteGridFragment);
                break;
            case R.id.nav_todo:
                break;
            case R.id.nav_voice:
                break;
            case R.id.nav_drawings:
                break;
            case R.id.nav_trash:
                mode = "";
                replaceFragment(trashNoteListFragment);
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
            case NoteManager.TRASH:
                break;
            default:
                break;
        }
    }

    @Override
    public void onNoteClick(int type, int position) {
        switch (type) {
            case NoteManager.NOTES:
                bundleNote(position);
                break;
            case NoteManager.TRASH:
                trashNoteDialog(position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNoteDelete(int type, boolean restore, Note note) {
        switch (type) {
            case NoteManager.NOTES:
                MyApplication.getNoteManager(context).moveNoteToTrash(note);
                break;
            case NoteManager.TRASH:
                if (restore)
                    MyApplication.getNoteManager(context).restoreNoteFromTrash(note);
                else
                    MyApplication.getNoteManager(context).deleteFromTrash(note);
                break;
            default:
                break;
        }
        hideDelete();
    }

    private void bundleNote(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        Launcher.launchActivity(context, bundle, WriteNoteActivity.class);
    }

    private void showDelete() {
        deleteArea.setVisibility(View.VISIBLE);
        Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        if (vibrator.hasVibrator())
            vibrator.vibrate(200);
    }

    private void hideDelete() {
        deleteArea.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, position);
        Launcher.launchActivity(context, bundle, WriteNoteActivity.class);
    }
}
