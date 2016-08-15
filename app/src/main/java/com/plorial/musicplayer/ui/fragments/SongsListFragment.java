package com.plorial.musicplayer.ui.fragments;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.plorial.musicplayer.MVP_Main;
import com.plorial.musicplayer.R;
import com.plorial.musicplayer.adapters.SongsArrayAdapter;
import com.plorial.musicplayer.presenter.ExplorerPresenter;
import com.plorial.musicplayer.presenter.Presenter;
import com.wnafee.vector.compat.ResourcesCompat;

/**
 * Created by plorial on 8/9/16.
 */
public class SongsListFragment extends Fragment implements AdapterView.OnItemClickListener, MVP_Main.RequiredSongsListOps{

    private static final String TAG = SongsListFragment.class.getSimpleName();

    public static final int REQUEST_PATH = 1;

    private MVP_Main.ProvidedPresenterPlaylist presenter;

    private ListView listView;
    private SongsArrayAdapter adapter;

    private Drawable play;
    private Drawable audioTrack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songs_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.songsList);
        adapter = new SongsArrayAdapter(getActivity(), R.layout.songs_list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        play = ResourcesCompat.getDrawable(getActivity(), R.drawable.ic_play_arrow_black_36dp);
        audioTrack = ResourcesCompat.getDrawable(getActivity(), R.drawable.audiotrack_black);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        presenter.selectSong(i);
    }

    @Override
    public void updateCurrentSong(int currentSongPos, int prevSong) {
        Log.d(TAG, "new song");
        AppCompatImageView songNew = (AppCompatImageView) listView.getChildAt(currentSongPos).findViewById(R.id.playSong);
        AppCompatImageView songOld = (AppCompatImageView) listView.getChildAt(prevSong).findViewById(R.id.playSong);
        songNew.setImageDrawable(play);
        songOld.setImageDrawable(audioTrack);
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        presenter.getAllSongs(adapter);
        presenter.setRequiredSongsListOps(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.options_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.search);
        MenuItem searchParams = menu.findItem(R.id.serch_params);
        final SubMenu params = searchParams.getSubMenu();
        SearchView searchView = (SearchView) searchItem.getActionView();

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        if(null!=searchManager ) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.search(getSearchOption(params),query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private SearchOption getSearchOption(SubMenu menu){
        if(menu.findItem(R.id.menu_album).isChecked()){
            return SearchOption.ALBUM;
        } else if(menu.findItem(R.id.menu_artist).isChecked()){
            return SearchOption.ARTIST;
        } else return SearchOption.TITLE;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_title:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                break;
            case R.id.menu_artist:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                break;
            case R.id.menu_album:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public enum SearchOption{
        TITLE, ARTIST, ALBUM
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PATH){
            if (resultCode == getActivity().RESULT_OK) {
                presenter.getSongsFromPath(adapter,data.getStringExtra(ExplorerPresenter.GET_PATH));
            }
        }
    }
}
