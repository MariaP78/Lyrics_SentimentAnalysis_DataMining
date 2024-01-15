package com.datamining.sentimentanalysis;

/**
 * The NDCG (Normalized Discounted Cumulative Gain) Calculator Class
 *
 */
public class NDCGCalculator {

    // Function to calculate NDCG (Normalized Discounted Cumulative Gain)
    public static double calculateNDCG(double dcg, double idcg) {
        // Calculate NDCG by dividing DCG by IDCG
        if (idcg == 0.0) {
            return 0.0; // To handle division by zero case
        }
        return dcg / idcg;
    }

    // Function to check if the ranking is perfect or not
    public static boolean checkRanking(double ndcg) {
        double epsilon = 1e-6; // used a small value to handle precision errors

        // Check if NDCG is close enough to 1.0
        double floor = Math.floor(Math.abs(ndcg));
        return floor < epsilon;
    }

}
