package com.datamining.sentimentanalysis;

/**
 * The Song Class
 * 
 * Used for storing the lyrics, sentiment score of a song amd its relevance score
 *
 */
public class Song implements Comparable<Song>{
    private String lyrics;
    private Double sentimentScore;
    private Double relevanceScore;

    // Constructor
    public Song(String lyrics, Double sentimentScore) {
        this.lyrics = lyrics;
        this.sentimentScore = sentimentScore;
        this.relevanceScore = 0.0;
    }

    // Getters and Setters
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

    // Compare songs based on their sentiment score
    @Override
    public int compareTo(Song other) {
        return Double.compare(other.sentimentScore, this.sentimentScore); // Descending order
    }

    // Override toString() method
    @Override
    public String toString() {
        return "Lyrics: " + lyrics + "\nScore: " + sentimentScore + "\nRelevanceScore: " + relevanceScore + "\n";
    }
    
}
