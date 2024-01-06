package com.datamining.sentimentanalysis.v1.Classifier;

import java.io.FileNotFoundException;

public class BayesMain {
    public static void main(String[] args) throws FileNotFoundException {
        String indexPath = "trainingSet.txt";

        NaiveBayes naiveBayes = new NaiveBayes(indexPath, true, true ,true ,true);
        naiveBayes.train();

    }
}
