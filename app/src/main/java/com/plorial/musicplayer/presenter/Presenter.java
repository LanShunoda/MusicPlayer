package com.plorial.musicplayer.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.plorial.musicplayer.MVP_Main;
import com.plorial.musicplayer.SongsArrayAdapter;
import com.plorial.musicplayer.pojo.SongsListItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by plorial on 8/9/16.
 */
public class Presenter implements MVP_Main.ProvidedPresenterPlaylist, MVP_Main.ProvidedPresenterOps, MVP_Main.RequiredPresenterOps{

    private static final String TAG = Presenter.class.getSimpleName();

    private MVP_Main.RequiredViewOps viewOps;
    private MVP_Main.ProvidedModelOps model;
    private MVP_Main.RequiredSongsListOps requiredSongsListOps;
    private MVP_Main.RequiredControlsOps requiredControlsOps;

    private SongsArrayAdapter adapter;
    private int currentSongNum;
    private Handler handler;
    private SongsListItem currentSong;

    public Presenter(MVP_Main.RequiredViewOps viewOps) {
        this.viewOps = viewOps;
    }

    public void getAllSongs(SongsArrayAdapter adapter){
        ContentResolver contentResolver = adapter.getContext().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            Log.e(TAG, "Error opening audio");
        } else if (!cursor.moveToFirst()) {
           Log.i(TAG, "No media on device");
            Toast.makeText(getActivityContext(),"No media on device", Toast.LENGTH_LONG).show();
        } else {
            List<SongsListItem> items = new ArrayList<>();
            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                SongsListItem songsListItem = new SongsListItem(title, album, artist, duration, data);
                items.add(songsListItem);
            } while (cursor.moveToNext());
            adapter.addAll(items);
        }
        this.adapter = adapter;
    }

    @Override
    public void selectSong(int position) {
        Log.d(TAG, "select song " + position);
        if(requiredSongsListOps != null) {
            requiredSongsListOps.updateCurrentSong(position, currentSongNum);
        }
        this.currentSongNum = position;
        currentSong = adapter.getItem(position);
        try {
            model.setData(currentSong.getData());
            model.prepare();
           play();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(requiredControlsOps != null)
        requiredControlsOps.updateSong(currentSong.getArtist(), currentSong.getTitle(), currentSong.getAlbum(), currentSong.getDuration());
    }

    @Override
    public void setRequiredSongsListOps(MVP_Main.RequiredSongsListOps requiredSongsListOps) {
        this.requiredSongsListOps = requiredSongsListOps;
    }

    @Override
    public void play() {
        model.play();
        startPlayProgressUpdater();
    }

    @Override
    public void pause() {
        model.pause();
    }

    @Override
    public void stop() {
        model.stop();
    }

    @Override
    public void next() {
        selectSong(currentSongNum + 1);
    }

    @Override
    public void prev() {
        selectSong(currentSongNum - 1);
    }

    @Override
    public void seekTo(int position) {
        model.seekTo(position);
    }

    @Override
    public Context getAppContext() {
        return viewOps.getAppContext();
    }

    @Override
    public Context getActivityContext() {
        return viewOps.getActivityContext();
    }

    public void setModel(MVP_Main.ProvidedModelOps model){
        this.model = model;
        model.setPresenter(this);
        model.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                next();
            }
        });
    }

    @Override
    public void setRequiredControlsOps(MVP_Main.RequiredControlsOps requiredControlsOps) {
        this.requiredControlsOps = requiredControlsOps;
        handler = requiredControlsOps.getHandler();
        if(model.isPlaying()){
            requiredControlsOps.updateSong(currentSong.getArtist(), currentSong.getTitle(), currentSong.getAlbum(), currentSong.getDuration());
            startPlayProgressUpdater();
        }
    }

    private void startPlayProgressUpdater() {
        Log.d(TAG, "start progress updater, requiredControls ops " + requiredControlsOps);
        if(requiredControlsOps != null) {
            requiredControlsOps.setProgress(model.getCurrentPosition());

            if (model.isPlaying()) {
                Runnable notification = new Runnable() {
                    public void run() {
                        startPlayProgressUpdater();
                    }
                };
                handler.postDelayed(notification, 1000);
            } else {
                model.pause();
            }
        }
    }
}
