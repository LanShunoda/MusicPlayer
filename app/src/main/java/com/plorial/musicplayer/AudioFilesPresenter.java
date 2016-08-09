package com.plorial.musicplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.plorial.musicplayer.pojo.SongsListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by plorial on 8/9/16.
 */
public class AudioFilesPresenter {

    private static final String TAG = AudioFilesPresenter.class.getSimpleName();

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
    }
}
