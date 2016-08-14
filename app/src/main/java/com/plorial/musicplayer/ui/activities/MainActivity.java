package com.plorial.musicplayer.ui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.plorial.musicplayer.servises.BackgroundAudioService;
import com.plorial.musicplayer.MVP_Main;
import com.plorial.musicplayer.R;
import com.plorial.musicplayer.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements MVP_Main.RequiredViewOps{

    private final static String TAG = MainActivity.class.getSimpleName();

    private Presenter presenter;
    private boolean bound = false;
    private MVP_Main.ProvidedModelOps serviceConnection;

    private Fragment[] requredFragmentOps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        requredFragmentOps = new Fragment[2];
        requredFragmentOps[0] = fragmentManager.findFragmentById(R.id.fragment_list);
        requredFragmentOps[1] = fragmentManager.findFragmentById(R.id.fragment_controls);
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
}
