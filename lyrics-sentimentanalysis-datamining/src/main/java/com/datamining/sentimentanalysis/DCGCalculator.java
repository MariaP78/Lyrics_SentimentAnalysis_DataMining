package com.datamining.sentimentanalysis;
import java.util.ArrayList;

public class DCGCalculator {

    // Function to calculate DCG
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

//    public static void main(String[] args) {
//        // Example relevance scores
//        ArrayList<Double> relevanceScores = new ArrayList<>();
//        relevanceScores.add(4.5); // Song 1
//        relevanceScores.add(3.8); // Song 2
//        relevanceScores.add(2.1); // Song 3
//        relevanceScores.add(1.9); // Song 4
//        relevanceScores.add(1.5); // Song 5
//
//        // Calculate DCG for the example scores
//        double dcg = calculateDCG(relevanceScores);
//        System.out.println("DCG: " + dcg);
//    }
}

