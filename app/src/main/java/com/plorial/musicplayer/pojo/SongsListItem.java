package com.plorial.musicplayer.pojo;

/**
 * Created by plorial on 8/9/16.
 */
public class SongsListItem {

    private String title;
    private String album;
    private String artist;
    private String duration;
    private String data;

    public SongsListItem(String title, String album, String artist, String duration, String data) {
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.duration = duration;
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public String getUri() {
        return data;
    }
}
