package com.plorial.musicplayer.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.plorial.musicplayer.MVP_Main;
import com.plorial.musicplayer.R;
import com.wnafee.vector.MorphButton;

/**
 * Created by plorial on 8/10/16.
 */
public class ControlsFragment extends Fragment implements View.OnClickListener{

    private MVP_Main.ProvidedPresenterOps presenter;

    private MorphButton playPauseButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.controls_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            presenter = activity.getProvidedPresenterOps();
        }
        setupView(view);
    }

    private void setupView(View view) {
        playPauseButton = (MorphButton) view.findViewById(R.id.playPauseBtn);
        playPauseButton.setOnClickListener(this);
        AppCompatImageButton stopButton = (AppCompatImageButton) view.findViewById(R.id.stopBtn);
        stopButton.setOnClickListener(this);
        AppCompatImageButton prevButton = (AppCompatImageButton) view.findViewById(R.id.prevBtn);
        prevButton.setOnClickListener(this);
        AppCompatImageButton nextButton = (AppCompatImageButton) view.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(this);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        TextView tvPlayedTime = (TextView) view.findViewById(R.id.tvPlayedTime);
        TextView tvDuration = (TextView) view.findViewById(R.id.tvDuration_controls);
        TextView tvArtistSong = (TextView) view.findViewById(R.id.tvArtistSong_controls);
        TextView tvAlbum = (TextView) view.findViewById(R.id.tvAlbum_controls);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.playPauseBtn:
                if(playPauseButton.getState() == MorphButton.MorphState.START){
                    presenter.play();
                    playPauseButton.setState(MorphButton.MorphState.END);
                } else {
                    presenter.pause();
                    playPauseButton.setState(MorphButton.MorphState.START);
                }
                break;
            case R.id.stopBtn:
                presenter.stop();
                break;
            case R.id.prevBtn:
                presenter.prev();
                break;
            case R.id.nextBtn:
                presenter.next();
                break;
        }
    }
}
