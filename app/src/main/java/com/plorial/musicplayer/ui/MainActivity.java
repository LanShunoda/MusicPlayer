package com.plorial.musicplayer.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.plorial.musicplayer.BackgroundAudioService;
import com.plorial.musicplayer.MVP_Main;
import com.plorial.musicplayer.R;
import com.plorial.musicplayer.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements MVP_Main.RequiredViewOps{

    private final static String TAG = MainActivity.class.getSimpleName();

    private Presenter presenter;
    private boolean bound = false;
    private MVP_Main.ProvidedModelOps serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, BackgroundAudioService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        presenter = new Presenter(getRequiredViewOps());
        setContentView(R.layout.activity_main);
    }

    @Override
    public Context getAppContext() {
        return getAppContext();
    }

    @Override
    public Context getActivityContext() {
        return this;
    }

    public MVP_Main.ProvidedPresenterPlaylist getProvidedPresenterPlaylist(){
        return presenter;
    }

    public MVP_Main.ProvidedPresenterOps getProvidedPresenterOps(){
        return presenter;
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
//            presenter = serviceConnection.getPresenter();
                presenter.setModel(serviceConnection);
            Log.i(TAG, "Service connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
            Log.i(TAG, "Service disconnected");
        }
    };
}
