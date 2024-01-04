package com.datamining.sentimentanalysis;

import java.util.ArrayList;
import java.util.Collections;

public class IDCGCalculator {

    // Function to calculate IDCG
    public static double calculateIDCG(ArrayList<Double> relevanceScores) {
        ArrayList<Double> sortedScores = new ArrayList<>(relevanceScores);
        Collections.sort(sortedScores, Collections.reverseOrder()); // Sort in descending order

        // Calculate IDCG using the same formula as DCG
        double idcg = 0.0;
        int numSongs = Math.min(relevanceScores.size(), sortedScores.size());

        for (int i = 0; i < numSongs; i++) {
            double relevance = sortedScores.get(i);
            int rank = i + 1;
            idcg += (relevance / (Math.log(rank + 1) / Math.log(2)));
        }

        return idcg;
    }

//    public static void main(String[] args) {
//        // Example relevance scores
//        ArrayList<Double> relevanceScores = new ArrayList<>();
//        relevanceScores.add(4.5); // Song 1
//        relevanceScores.add(3.8); // Song 2
//        relevanceScores.add(2.1); // Song 3
//        relevanceScores.add(1.9); // Song 4
//        relevanceScores.add(1.5); // Song 5
//
//        // Calculate IDCG for the example scores
//        double idcg = calculateIDCG(relevanceScores);
//        System.out.println("IDCG: " + idcg);
//    }
}
