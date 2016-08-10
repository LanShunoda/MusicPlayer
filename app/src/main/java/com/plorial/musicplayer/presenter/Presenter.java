package com.plorial.musicplayer.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

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

    private SongsArrayAdapter adapter;
    private MVP_Main.ProvidedModelOps model;

    public Presenter(MVP_Main.RequiredViewOps viewOps) {
        this.viewOps = viewOps;
    }

    public void getAllSongs(SongsArrayAdapter adapter){
        ContentResolver contentResolver = adapter.getContext().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // query failed, handle error.
        } else if (!cursor.moveToFirst()) {
            // no media on the device
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
        Log.i(TAG, "Select song");
        SongsListItem currentSong = adapter.getItem(position);
        try {
            model.setData(currentSong.getData());
            model.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {

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
    }
}
