package com.plorial.musicplayer.presenter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import com.plorial.musicplayer.adapters.FolderArrayAdapter;
import com.plorial.musicplayer.pojo.FolderListItem;
import com.plorial.musicplayer.utils.AudioFileFilter;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by plorial on 8/13/16.
 */
public class ExplorerPresenter {

    public static final String GET_PATH = "GetPath";
    private File currentDir;
    private FolderArrayAdapter adapter;
    private Activity activity;

    public ExplorerPresenter(Activity activity, File currentDir, FolderArrayAdapter adapter) {
        this.activity = activity;
        this.currentDir = currentDir;
        this.adapter = adapter;
    }

    public void fill(){
        fill(currentDir);
    }

    private void fill(File dir){
        File[] dirs = dir.listFiles(new AudioFileFilter());
        activity.setTitle("Current Dir: " + dir.getName());
        List<FolderListItem> directory = new ArrayList<FolderListItem>();
        for(File ff: dirs) {
            Date lastModDate = new Date(ff.lastModified());
            DateFormat formater = DateFormat.getDateTimeInstance();
            String date_modify = formater.format(lastModDate);
            if(ff.isDirectory()){

                File[] fbuf = ff.listFiles();
                int buf = 0;
                if(fbuf != null){
                    buf = fbuf.length;
                } else buf = 0;
                String num_item = String.valueOf(buf);
                if(buf == 0) num_item = num_item + " item";
                else num_item = num_item + " items";

                directory.add(new FolderListItem(ff.getName(),num_item,date_modify,ff.getAbsolutePath()));
            }
        }
        Collections.sort(directory);
        adapter.clear();
        if(!dir.getName().equalsIgnoreCase("sdcard"))
            adapter.add(new FolderListItem("UP","","",dir.getParent()));
        adapter.addAll(directory);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        FolderListItem item = adapter.getItem(position);
        if (item.getName().contains("UP")) {
            if (item.getPath() == null) {
                return;
            } else {
                currentDir = new File(item.getPath());
                fill(currentDir);
            }
        } else {
            currentDir = new File(item.getPath());
            fill(currentDir);
        }
    }

    public void onAddFolder(int position){
        FolderListItem item = adapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra(GET_PATH, item.getPath());
        activity.setResult(Activity.RESULT_OK, intent);
        activity.finish();
    }

    public File getCurrentDir() {
        return currentDir;
    }
}
