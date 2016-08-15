package com.plorial.musicplayer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.plorial.musicplayer.R;
import com.plorial.musicplayer.pojo.FolderListItem;

/**
 * Created by plorial on 8/13/16.
 */
public class FolderArrayAdapter extends ArrayAdapter<FolderListItem> {

    private Context context;
    private int resource;

    public FolderArrayAdapter(Context context, int resource) {
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

        FolderListItem item = super.getItem(position);
        if(item != null){
            TextView folderName = (TextView) view.findViewById(R.id.tvFolderName);
            TextView itemsCount = (TextView) view.findViewById(R.id.tvItemsCount);
            TextView date = (TextView) view.findViewById(R.id.tvDate);
            folderName.setText(item.getName());
            itemsCount.setText(item.getItemsCount());
            date.setText(item.getDate());
        }
        return view;
    }
}
