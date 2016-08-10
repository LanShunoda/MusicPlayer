package com.plorial.musicplayer;

import android.content.Context;

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

    }

    interface ProvidedPresenterPlaylist {
        void getAllSongs(SongsArrayAdapter adapter);
        void selectSong(int position);
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

    }
}
