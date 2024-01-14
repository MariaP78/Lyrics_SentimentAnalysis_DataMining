package com.datamining.sentimentanalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The IDCG (Ideal Discounted Cumulative Gain) Calculator Class
 */
public class IDCGCalculator {

    // Function to calculate IDCG (Ideal Discounted Cumulative Gain)
    public static double calculateIDCG(List<Double> relevanceScores) {
        List<Double> sortedScores = new ArrayList<>(relevanceScores);
        sortedScores.sort(Collections.reverseOrder()); // Sort in descending order

        // Calculate IDCG using the same formula as DCG
        double idcg = 0.0;
        // calculate the number of songs by taking the minimum between 
        // the number of relevance scores and the number of sorted scores
        int numSongs = Math.min(relevanceScores.size(), sortedScores.size());

        for (int i = 0; i < numSongs; i++) {
            double relevance = sortedScores.get(i);
            int rank = i + 1;
            idcg += (relevance / (Math.log(rank + 1) / Math.log(2)));
        }

        return idcg;
    }
}
