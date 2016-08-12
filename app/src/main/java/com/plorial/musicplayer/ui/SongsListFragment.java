package com.plorial.musicplayer.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.plorial.musicplayer.MVP_Main;
import com.plorial.musicplayer.R;
import com.plorial.musicplayer.SongsArrayAdapter;
import com.plorial.musicplayer.presenter.Presenter;
import com.wnafee.vector.compat.VectorDrawable;

/**
 * Created by plorial on 8/9/16.
 */
public class SongsListFragment extends Fragment implements AdapterView.OnItemClickListener, MVP_Main.RequiredSongsListOps{

    private static final String TAG = SongsListFragment.class.getSimpleName();

    private ListView listView;
    private SongsArrayAdapter adapter;
    private MVP_Main.ProvidedPresenterPlaylist presenter;

    private VectorDrawable play;
    private VectorDrawable audioTrack;

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
        listView.setOnItemClickListener(this);
        play = VectorDrawable.getDrawable(getActivity(), R.drawable.ic_play_arrow_black_36dp);
        audioTrack = VectorDrawable.getDrawable(getActivity(), R.drawable.audiotrack_black);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        presenter.selectSong(i);
    }

    @Override
    public void updateCurrentSong(int currentSongPos, int prevSong) {
//        AppCompatImageView songNew = (AppCompatImageView) listView.getChildAt(currentSongPos).findViewById(R.id.playSong);
//        AppCompatImageView songOld = (AppCompatImageView) listView.getChildAt(prevSong).findViewById(R.id.playSong);
//        songNew.setImageDrawable(play);
//        songOld.setImageDrawable(audioTrack);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        presenter.getAllSongs(adapter);
        presenter.setRequiredSongsListOps(this);
    }
}
