package com.plorial.musicplayer.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.plorial.musicplayer.AudioFilesPresenter;
import com.plorial.musicplayer.R;
import com.plorial.musicplayer.SongsArrayAdapter;

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