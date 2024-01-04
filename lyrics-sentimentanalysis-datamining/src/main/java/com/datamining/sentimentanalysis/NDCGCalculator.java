package com.datamining.sentimentanalysis;

public class NDCGCalculator {

    // Function to calculate NDCG
    public static double calculateNDCG(double dcg, double idcg) {
        // Calculate NDCG by dividing DCG by IDCG
        if (idcg == 0.0) {
            return 0.0; // To handle division by zero case
        }
        return dcg / idcg;
    }

    // Function to check if the ranking is perfect
    public static boolean isPerfectRanking(double ndcg) {
        double epsilon = 1e-6; // A small value to handle precision errors

        // Check if NDCG is close enough to 1.0
        return Math.abs(ndcg - 1.0) < epsilon;
    }

}
