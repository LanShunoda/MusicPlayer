package com.plorial.musicplayer.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.plorial.musicplayer.MVP_Main;
import com.plorial.musicplayer.R;
import com.plorial.musicplayer.presenter.Presenter;
import com.plorial.musicplayer.servises.BackgroundAudioService;
import com.plorial.musicplayer.ui.fragments.SongsListFragment;
import com.plorial.musicplayer.utils.SongsSort;

public class MainActivity extends AppCompatActivity implements MVP_Main.RequiredViewOps, NavigationView.OnNavigationItemSelectedListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private Presenter presenter;
    private boolean bound = false;
    private MVP_Main.ProvidedModelOps serviceConnection;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Fragment[] requredFragmentOps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        FragmentManager fragmentManager = getFragmentManager();
        requredFragmentOps = new Fragment[2];
        requredFragmentOps[0] = fragmentManager.findFragmentById(R.id.fragment_list);
        requredFragmentOps[1] = fragmentManager.findFragmentById(R.id.fragment_controls);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BackgroundAudioService.class);
        getApplicationContext().startService(intent);
        getApplicationContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public Context getAppContext() {
        return getAppContext();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    private MVP_Main.RequiredViewOps getRequiredViewOps(){
        return this;
    }

    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BackgroundAudioService.LocalBinder binder = (BackgroundAudioService.LocalBinder) service;
            serviceConnection = binder.getService();
            bound = true;
            presenter = serviceConnection.getPresenter();
            if(presenter == null){
                Log.d(TAG, "new presenter");
                presenter = new Presenter(getRequiredViewOps());
                presenter.setModel(serviceConnection);
            }
            for (Fragment f :requredFragmentOps){
                if(f instanceof MVP_Main.RequiredFragmentsOps){
                    Log.d(TAG, "set presenter");
                    ((MVP_Main.RequiredFragmentsOps)f).setPresenter(presenter);
                }
            }
            Log.i(TAG, "Service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
            Log.i(TAG, "Service disconnected");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bound && connection != null) {
            try {
                getActivityContext().unbindService(connection);
            }catch (IllegalArgumentException e){}
            bound = false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.openFolder:
                presenter.startFileExplorerActivity(SongsListFragment.REQUEST_PATH, requredFragmentOps[0]);
                break;
            case R.id.sort_title:
                presenter.sort(new SongsSort.SortByTitle());
                break;
            case R.id.sort_album:
                presenter.sort(new SongsSort.SortByAlbum());
                break;
            case R.id.sort_artist:
                presenter.sort(new SongsSort.SortByArtist());
                break;
            case R.id.sort_duration:
                presenter.sort(new SongsSort.SortByDuration());
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
