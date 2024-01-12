package com.datamining.sentimentanalysis;

import java.util.ArrayList;

/**
 * The DCG (Discounted Cumulative Gain) Calculator Class
 *
 */
public class DCGCalculator {

    // Function to calculate the DCG (Discounted Cumulative Gain)
    public static double calculateDCG(ArrayList<Double> relevanceScores) {
        double dcg = 0.0;
        int numSongs = relevanceScores.size();

        // Calculate DCG
        for (int i = 0; i < numSongs; i++) {
            double relevance = relevanceScores.get(i);
            int rank = i + 1;
            dcg += (relevance / (Math.log(rank + 1) / Math.log(2)));
        }

        return dcg;
    }
}
