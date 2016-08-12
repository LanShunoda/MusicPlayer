package com.plorial.musicplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import com.plorial.musicplayer.presenter.Presenter;

import java.io.IOException;

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

    interface RequiredSongsListOps{
        void updateCurrentSong(int currentSongPos, int prevSong);
    }

    interface RequiredControlsOps{
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
        void selectSong(int position);
        void setRequiredSongsListOps(RequiredSongsListOps requiredSongsListOps);
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

    }
}
