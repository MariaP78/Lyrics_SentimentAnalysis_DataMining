package com.datamining.sentimentanalysis;

public class Song implements Comparable<Song>{
    private String lyrics;
    private Double sentimentScore;
    private Double relevanceScore;

    public Song(String lyrics, Double sentimentScore) {
        this.lyrics = lyrics;
        this.sentimentScore = sentimentScore;
        this.relevanceScore = 0.0;
    }

    public String getLyrics() {
        return lyrics;
    }

    public Double getSentimentScore() {
        return sentimentScore;
    }

    public Double getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(Double relevanceScore) {
        this.relevanceScore = relevanceScore;
    }

    @Override
    public int compareTo(Song other) {
        return Double.compare(other.sentimentScore, this.sentimentScore); // Descending order
    }

    @Override
    public String toString() {
        return "Lyrics: " + lyrics + "\nScore: " + sentimentScore + "\nRelevanceScore: " + relevanceScore + "\n";
    }
    
}
