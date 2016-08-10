package com.plorial.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

/**
 * Created by plorial on 8/10/16.
 */
public class BackgroundAudioService extends Service implements MVP_Main.ProvidedModelOps{

    private final static String TAG = BackgroundAudioService.class.getSimpleName();

    private final IBinder binder = new LocalBinder();

    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        Log.i(TAG, "Service created");
    }

    @Override
    public void play() {
        Log.i(TAG, "Service play");
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void next() {

    }

    @Override
    public void prev() {

    }

    @Override
    public void seekTo(int position) {
        player.seekTo(position);
    }

    @Override
    public void setData(String data) throws IOException {
        Log.i(TAG, "Service setData " + data);
        player.setDataSource(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();
    }

    public class LocalBinder extends Binder {
        public MVP_Main.ProvidedModelOps getService() {
            return BackgroundAudioService.this;
        }
    }
}
