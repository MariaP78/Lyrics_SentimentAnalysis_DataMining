package com.datamining.sentimentanalysis;

import java.util.List;

public class RelevanceScorer {

    // LINEAR SCORING
    public static void assignRelevanceScoresLinearly(List<Song> rankedSongs) {
        // Assuming the highest relevance score is equal to the number of songs
        // and decreases by 1 for each subsequent song
        int maxScore = rankedSongs.size();
        for (int i = 0; i < rankedSongs.size(); i++) {
            // Assigning scores in descending order starting from maxScore
            rankedSongs.get(i).setRelevanceScore(Double.valueOf(maxScore - i));
        }
    }

    // LOGARITHMIC SCORING
    public static void assignLogarithmicRelevanceScores(List<Song> rankedSongs) {
        // Assuming the highest relevance score is for the top-ranked song
        // and decreases logarithmically for each subsequent song
        final double baseLogRelevance = Math.log(rankedSongs.size());
        for (int i = 0; i < rankedSongs.size(); i++) {
            // Using logarithmic scale, ensuring that the top song has the highest score
            double relevanceScore = baseLogRelevance - Math.log(i + 1);
            rankedSongs.get(i).setRelevanceScore(Double.parseDouble(String.format("%.2f", relevanceScore)));
        }
    }
}