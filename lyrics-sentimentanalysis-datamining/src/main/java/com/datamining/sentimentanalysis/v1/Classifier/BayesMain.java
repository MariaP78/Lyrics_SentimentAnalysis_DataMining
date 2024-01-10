package com.datamining.sentimentanalysis.v1.Classifier;

import java.io.IOException;

public class BayesMain {
    public static void main(String[] args) throws IOException {
        // path for the set used for training
        String indexPath = "trainingSet.txt";

        NaiveBayes naiveBayes = new NaiveBayes(indexPath, true, false ,true ,true);
        naiveBayes.train();

        //set used for testing
        naiveBayes.classifyFile("testingSet.txt");

        // final Map<String, Integer> stringIntegerMap = naiveBayes.classifyFile("testingSet.txt");
        // System.out.println(stringIntegerMap);
    }
}
