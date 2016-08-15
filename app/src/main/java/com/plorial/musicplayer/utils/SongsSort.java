package com.plorial.musicplayer.utils;

import com.plorial.musicplayer.pojo.SongsListItem;

import java.util.Comparator;

/**
 * Created by plorial on 8/15/16.
 */
public class SongsSort {

    private SongsSort() {}

    public static class SortByTitle implements Comparator<SongsListItem>{

        @Override
        public int compare(SongsListItem i1, SongsListItem i2) {
            return i1.getTitle().compareToIgnoreCase(i2.getTitle());
        }
    }

    public static class SortByArtist implements Comparator<SongsListItem>{

        @Override
        public int compare(SongsListItem i1, SongsListItem i2) {
            return i1.getArtist().compareToIgnoreCase(i2.getArtist());
        }
    }

    public static class SortByAlbum implements Comparator<SongsListItem>{

        @Override
        public int compare(SongsListItem i1, SongsListItem i2) {
            return i1.getAlbum().compareToIgnoreCase(i2.getAlbum());
        }
    }

    public static class SortByDuration implements Comparator<SongsListItem>{

        @Override
        public int compare(SongsListItem i1, SongsListItem i2) {
            int d1;
            int d2;
            if(i1.getDuration() == null){
                return -1;
            } else if(i2.getDuration() == null){
                return 1;
            } else {
                d1 = Integer.parseInt(i1.getDuration());
                d2 = Integer.parseInt(i2.getDuration());
            }

            if(d1 > d2) {
                return 1;
            }
            else if(d1 < d2) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    public static class SortByRandom implements Comparator<SongsListItem>{

        @Override
        public int compare(SongsListItem i1, SongsListItem i2) {
            return Math.random() < 0.5 ? 1 : -1;
        }
    }
}
