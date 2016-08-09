package com.plorial.musicplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

/**
 * Created by plorial on 8/9/16.
 */
public class AudioFilesPresenter {

    private static final String TAG = AudioFilesPresenter.class.getSimpleName();

    public void getAllSongs(ArrayAdapter<String> adapter){
        ContentResolver contentResolver = adapter.getContext().getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // query failed, handle error.
        } else if (!cursor.moveToFirst()) {
            // no media on the device
        } else {
            do {
                String fullPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                // ...process entry...
                Log.d(TAG, fullPath);
                adapter.add(fullPath);
            } while (cursor.moveToNext());
        }
    }
}
