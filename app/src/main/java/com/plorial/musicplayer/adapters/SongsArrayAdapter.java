package com.plorial.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.plorial.musicplayer.R;
import com.plorial.musicplayer.pojo.SongsListItem;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by plorial on 8/9/16.
 */
public class SongsArrayAdapter extends ArrayAdapter<SongsListItem> {

    private Context context;
    private int resource;

    public SongsArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, null);
        }

        SongsListItem item = super.getItem(position);
        if(item != null){
            TextView tvArtistSong = (TextView) view.findViewById(R.id.tvArtistSong);
            TextView tvAlbum = (TextView) view.findViewById(R.id.tvAlbum);
            TextView tvDuration = (TextView) view.findViewById(R.id.tvDuration);
            tvArtistSong.setText(item.getArtist() + " - " + item.getTitle());
            tvAlbum.setText(item.getAlbum());
            long d;
            if(item.getDuration() != null) {
                 d = Long.parseLong(item.getDuration());
            }else {
                d = 0;
            }
            SimpleDateFormat format = new SimpleDateFormat("mm:ss");
            String duration = format.format(new Date(d));
            tvDuration.setText(duration);
        }
        return view;
    }
}
