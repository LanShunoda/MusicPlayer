package com.plorial.musicplayer.utils;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 * Created by plorial on 8/13/16.
 */
public class AudioFileFilter implements FileFilter {

    private static final String TAG = AudioFileFilter.class.getSimpleName();

    @Override
    public boolean accept(File f) {
        if (f.isHidden() || !f.canRead()) {
            return false;
        }

        if (f.isDirectory()) {
            return checkDirectory(f);
        }
        return checkFileExtension(f);
    }

    private boolean checkFileExtension(File f) {
        String ext = getFileExtension(f);
        if (ext == null) return false;
        try {
            if (SupportedFileFormat.valueOf(ext.toUpperCase()) != null) {
                return true;
            }
        } catch(IllegalArgumentException e) {
            //Not known enum value
            return false;
        }
        return false;
    }

    private boolean checkDirectory(File dir) {
            final ArrayList<File> subDirs = new ArrayList<File>();
            int songNumb = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.isFile()) {
                        if (file.getName().equals( ".nomedia" ) )
                            return false;

                        return checkFileExtension( file );
                    } else if ( file.isDirectory() ){
                        subDirs.add( file );
                        return false;
                    } else
                        return false;
                }
            } ).length;

            if (songNumb > 0) {
                Log.i(TAG, "checkDirectory: dir " + dir.toString() + " return true con songNumb -> " + songNumb );
                return true;
            }

            for(File subDir: subDirs) {
                if ( checkDirectory( subDir ) ) {
                    Log.i(TAG, "checkDirectory [for]: subDir " + subDir.toString() + " return true" );
                    return true;
                }
            }
            return false;
    }

    private boolean checkFileExtension(String fileName) {
        String ext = getFileExtension(fileName);
        if ( ext == null) return false;
        try {
            if (SupportedFileFormat.valueOf(ext.toUpperCase()) != null) {
                return true;
            }
        } catch(IllegalArgumentException e) {
            //Not known enum value
            return false;
        }
        return false;
    }

    private String getFileExtension(File f) {
        return getFileExtension( f.getName() );
    }

    private String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i+1);
        } else
            return null;
    }

    public enum SupportedFileFormat {
        _3GP("3gp"),
        MP4("mp4"),
        M4A("m4a"),
        AAC("aac"),
        TS("ts"),
        FLAC("flac"),
        MP3("mp3"),
        MID("mid"),
        XMF("xmf"),
        MXMF("mxmf"),
        RTTTL("rtttl"),
        RTX("rtx"),
        OTA("ota"),
        IMY("imy"),
        OGG("ogg"),
        MKV("mkv"),
        WAV("wav");

        private String filesuffix;

        SupportedFileFormat(String filesuffix) {
            this.filesuffix = filesuffix;
        }

        public String getFilesuffix() {
            return filesuffix;
        }
    }

}
