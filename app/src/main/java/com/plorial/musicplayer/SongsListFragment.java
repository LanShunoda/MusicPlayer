package com.plorial.musicplayer;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.plorial.musicplayer.pojo.SongsListItem;

/**
 * Created by plorial on 8/9/16.
 */
public class SongsListFragment extends Fragment {

    private static final String TAG = SongsListFragment.class.getSimpleName();

    private ListView listView;
    private SongsArrayAdapter adapter;
    private AudioFilesPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songs_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.listView);
        adapter = new SongsArrayAdapter(getActivity(), R.layout.songs_list_item);
        listView.setAdapter(adapter);
        presenter = new AudioFilesPresenter();
        presenter.getAllSongs(adapter);
    }
}
