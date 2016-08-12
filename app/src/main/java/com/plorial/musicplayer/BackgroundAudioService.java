package com.plorial.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.plorial.musicplayer.presenter.Presenter;

import java.io.IOException;

/**
 * Created by plorial on 8/10/16.
 */
public class BackgroundAudioService extends Service implements MVP_Main.ProvidedModelOps{

    private final static String TAG = BackgroundAudioService.class.getSimpleName();

    private final IBinder binder = new LocalBinder();

    private MediaPlayer player;
    private Presenter presenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(player == null)
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
        player.pause();
    }

    @Override
    public void stop() {
        player.stop();
    }

    @Override
    public void next() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void prev() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void seekTo(int position) {
        player.seekTo(position);
    }

    @Override
    public void setData(String data) throws IOException {
        Log.i(TAG, "Service setData " + data);
//        if(player.isPlaying()){
            player.reset();
//        }
        player.setDataSource(data);
    }

    @Override
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        player.setOnCompletionListener(listener);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        player.release();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    public class LocalBinder extends Binder {
        public MVP_Main.ProvidedModelOps getService() {
            return BackgroundAudioService.this;
        }
    }
}
