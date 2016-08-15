package com.plorial.musicplayer.ui.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.plorial.musicplayer.R;
import com.plorial.musicplayer.adapters.FolderArrayAdapter;
import com.plorial.musicplayer.presenter.ExplorerPresenter;

import java.io.File;

/**
 * Created by plorial on 8/13/16.
 */
public class FolderExplorerActivity extends ListActivity implements AdapterView.OnItemLongClickListener{

    private static final String TAG = FolderExplorerActivity.class.getSimpleName();

    private ExplorerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listView = getListView();
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        FolderArrayAdapter adapter = new FolderArrayAdapter(this, R.layout.explorer_item);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
        File currentDir;
        if(savedInstanceState != null){
            currentDir = new File(savedInstanceState.getString(TAG));
        } else {
            currentDir = new File(Environment.getExternalStorageDirectory(), "");
        }
        presenter = new ExplorerPresenter(this, currentDir, adapter);
        presenter.fill();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, R.string.long_click, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG, presenter.getCurrentDir().getAbsolutePath());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(TAG, "onlistItemClick " + position);
        presenter.onListItemClick(l, v, position, id);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        presenter.onAddFolder(i);
        return false;
    }
}
