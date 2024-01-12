package com.datamining.sentimentanalysis;

/**
 * 
 * The SongRequest Class
 * 
 */
public class SongRequest {
    private String lyrics;

    public SongRequest() {
    }

    public SongRequest(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics= lyrics;
    }
}
