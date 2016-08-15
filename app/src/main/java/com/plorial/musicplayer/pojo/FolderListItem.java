package com.plorial.musicplayer.pojo;

/**
 * Created by plorial on 8/13/16.
 */
public class FolderListItem implements Comparable<FolderListItem> {

    private String name;
    private String itemsCount;
    private String date;
    private String path;

    public FolderListItem(String name, String itemsCount, String date, String path) {
        this.name = name;
        this.itemsCount = itemsCount;
        this.date = date;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getItemsCount() {
        return itemsCount;
    }

    public String getDate() {
        return date;
    }

    public String getPath() {
        return path;
    }

    @Override
    public int compareTo(FolderListItem item) {
        if(this.name != null)
            return this.name.toLowerCase().compareTo(item.getName().toLowerCase());
        else
            throw new IllegalArgumentException();
    }
}
