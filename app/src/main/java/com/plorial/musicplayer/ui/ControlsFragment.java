package com.plorial.musicplayer.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by plorial on 8/10/16.
 */
public class ControlsFragment extends Fragment implements View.OnClickListener, MVP_Main.RequiredControlsOps{

    private MVP_Main.ProvidedPresenterOps presenter;

    private Handler handler = new Handler();

    private MorphButton playPauseButton;
    private SeekBar seekBar;
    private TextView tvPlayedTime;
    private TextView tvDuration;
    private TextView tvArtistSong;
    private TextView tvAlbum;

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
            presenter.setRequiredControlsOps(this);
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

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        tvPlayedTime = (TextView) view.findViewById(R.id.tvPlayedTime);

        tvDuration = (TextView) view.findViewById(R.id.tvDuration_controls);

        tvArtistSong = (TextView) view.findViewById(R.id.tvArtistSong_controls);

        tvAlbum = (TextView) view.findViewById(R.id.tvAlbum_controls);
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

    @Override
    public void updateSong(String artist, String song, String album, String duration) {
        tvArtistSong.setText(artist + " - " + song);
        tvAlbum.setText(album);
        long d;
        if(duration != null) d = Long.parseLong(duration);
        else d = 0;
        seekBar.setMax((int) d);
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        String time = format.format(new Date(d));
        tvDuration.setText(time);
    }

    @Override
    public void setProgress(int progress) {
        seekBar.setProgress(progress);
    }

    @Override
    public Handler getHandler() {
        return handler;
    }
}
