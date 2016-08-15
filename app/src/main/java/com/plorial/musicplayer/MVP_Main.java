package com.plorial.musicplayer;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import com.plorial.musicplayer.adapters.SongsArrayAdapter;
import com.plorial.musicplayer.pojo.SongsListItem;
import com.plorial.musicplayer.presenter.Presenter;
import com.plorial.musicplayer.ui.fragments.SongsListFragment;

import java.io.IOException;
import java.util.Comparator;

/**
 * Created by plorial on 8/10/16.
 */
public interface MVP_Main {
    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     */
    interface RequiredViewOps {
        Context getAppContext();
        Context getActivityContext();
    }

    interface RequiredFragmentsOps{
        void setPresenter(Presenter presenter);
    }

    interface RequiredSongsListOps extends RequiredFragmentsOps{
        void updateCurrentSong(int currentSongPos, int prevSong);
    }

    interface RequiredControlsOps extends RequiredFragmentsOps{
        void updateSong(String artist, String song, String album, String duration);
        void setProgress(int progress);
        Handler getHandler();
    }

    interface Controls{
        void play();
        void pause();
        void stop();
        void next();
        void prev();
        void loop(boolean l);
        void seekTo(int position);}

    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface ProvidedPresenterOps extends Controls {
        // Presenter operations permitted to View
        void setRequiredControlsOps(RequiredControlsOps requiredControlsOps);
    }

    interface ProvidedPresenterPlaylist {
        void getAllSongs(SongsArrayAdapter adapter);
        void getSongsFromPath(SongsArrayAdapter adapter, String path);
        void selectSong(int position);
        void setRequiredSongsListOps(RequiredSongsListOps requiredSongsListOps);
        void search(SongsListFragment.SearchOption option, String query);
        void startFileExplorerActivity(int request, Fragment fragment);
        void sort(Comparator<SongsListItem> comparator);
    }

    /**
     * Required Presenter methods available to Model.
     */
    interface RequiredPresenterOps {
        // Presenter operations permitted to Model
        Context getAppContext();
        Context getActivityContext();
    }

    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     */
    interface ProvidedModelOps extends Controls {
        void setData(String data) throws IOException;
        void setOnCompletionListener(MediaPlayer.OnCompletionListener listener);
        void setPresenter(Presenter presenter);
        Presenter getPresenter();
        boolean isPlaying();
        int getCurrentPosition();
        void prepare();
    }
}
