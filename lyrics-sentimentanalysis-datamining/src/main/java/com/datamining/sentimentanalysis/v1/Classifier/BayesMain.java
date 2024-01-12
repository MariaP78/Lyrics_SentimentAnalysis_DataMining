package com.datamining.sentimentanalysis.v1.Classifier;

import java.io.IOException;

/**
 * The Bayes Main Class
 * 
 * Used for training the classifier and classifying the testing set
 *
 */
public class BayesMain {
    public static void main(String[] args) throws IOException {
        // path to the training set
        String indexPath = "../Lyrics_SentimentAnalysis_DataMining/lyrics-sentimentanalysis-datamining/trainingSet.txt";

        // train the classifier
        NaiveBayes naiveBayes = new NaiveBayes(indexPath, true, false ,true ,true);
        naiveBayes.train();

        // classify the testing set
        naiveBayes.classifyFile("../Lyrics_SentimentAnalysis_DataMining/lyrics-sentimentanalysis-datamining/testingSet.txt");
    }
}
