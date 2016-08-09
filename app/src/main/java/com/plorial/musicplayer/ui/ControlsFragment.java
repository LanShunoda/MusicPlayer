package com.plorial.musicplayer.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plorial.musicplayer.R;

/**
 * Created by plorial on 8/10/16.
 */
public class ControlsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.controls_fragment, container, false);
        return view;
    }
}
