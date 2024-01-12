package com.datamining.sentimentanalysis;

/**
 * 
 * The SongResponse Class
 * 
 */
public class SongResponse {
    private String sentiment;

    public SongResponse() {
    }

    public SongResponse(String sentiment) {
        this.sentiment = sentiment;
    }

    public String getSentiment() {
        return sentiment;
    }
}
