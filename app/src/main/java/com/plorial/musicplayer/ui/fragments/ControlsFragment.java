package com.plorial.musicplayer.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.plorial.musicplayer.MVP_Main;
import com.plorial.musicplayer.R;
import com.plorial.musicplayer.presenter.Presenter;
import com.wnafee.vector.MorphButton;

/**
 * Created by plorial on 8/10/16.
 */
public class ControlsFragment extends Fragment implements View.OnClickListener, MVP_Main.RequiredControlsOps, CompoundButton.OnCheckedChangeListener{

    private static final String TAG = ControlsFragment.class.getSimpleName();

    private MVP_Main.ProvidedPresenterOps presenter;

    private Handler handler = new Handler();

    private SeekBar seekBar;
    private TextView tvPlayedTime;
    private TextView tvDuration;
    private TextView tvArtistSong;
    private TextView tvAlbum;
    private Switch switchLoop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.controls_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView(view);
    }

    private void setupView(View view) {
        MorphButton playPauseButton = (MorphButton) view.findViewById(R.id.playPauseBtn);
        playPauseButton.setOnStateChangedListener(new MorphButton.OnStateChangedListener() {
            @Override
            public void onStateChanged(MorphButton.MorphState changedTo, boolean isAnimating) {
                playPause(changedTo);
            }
        });
        AppCompatImageButton stopButton = (AppCompatImageButton) view.findViewById(R.id.stopBtn);
        stopButton.setOnClickListener(this);
        AppCompatImageButton prevButton = (AppCompatImageButton) view.findViewById(R.id.prevBtn);
        prevButton.setOnClickListener(this);
        AppCompatImageButton nextButton = (AppCompatImageButton) view.findViewById(R.id.nextBtn);
        nextButton.setOnClickListener(this);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(presenter != null) {
                    presenter.seekTo(i);
                }
                seekBar.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        tvPlayedTime = (TextView) view.findViewById(R.id.tvPlayedTime);

        tvDuration = (TextView) view.findViewById(R.id.tvDuration_controls);

        tvArtistSong = (TextView) view.findViewById(R.id.tvArtistSong_controls);

        tvAlbum = (TextView) view.findViewById(R.id.tvAlbum_controls);
        switchLoop = (Switch) view.findViewById(R.id.loop);
        switchLoop.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
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

    private void playPause(MorphButton.MorphState changedTo) {
        if(presenter != null && changedTo.equals(MorphButton.MorphState.END)){
            presenter.pause();
        }else if (presenter != null && changedTo.equals(MorphButton.MorphState.START)){
            presenter.play();
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
        tvDuration.setText(progressToTime((int) d));
    }

    @Override
    public void setProgress(int progress) {
        seekBar.setProgress(progress);
        tvPlayedTime.setText(progressToTime(progress));
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
        presenter.setRequiredControlsOps(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    private static String progressToTime(int progress){
        long second = (progress / 1000) % 60;
        long minute = (progress / (1000 * 60)) % 60;

        return String.format("%02d:%02d",minute, second);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switchLoop.setChecked(b);
        presenter.loop(b);
    }
}
