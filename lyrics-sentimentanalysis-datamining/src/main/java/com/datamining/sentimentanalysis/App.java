package com.datamining.sentimentanalysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lyrics Sentiment Analysis Data Mining Main Class
 */
public class App {
    public static void main(String[] args) {

        // read the lyrics of the songs from our dataset
        String filePath = "../Lyrics_SentimentAnalysis_DataMining/Dataset_Songs_Lyrics.txt";
        List<String> songs = LyricsReader.readLyrics(filePath);

        // use the SentimentAnalyzer class to analyze the sentiment of each song
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        sentimentAnalyzer.analyzeSentiment(songs);

        // SONGS RANKING based on their sentiment from -1 to 3 (sentiment score)
        // Very Negative = -1.0
        // Negative = 0.0
        // Neutral = 1.0
        // Positive = 2.0
        // Very Positive = 3.0

        // get the ranked songs list
        List<Song> rankedSongsList = sentimentAnalyzer.rankedSongs;

        System.out.println("Standford NLP - Performance:");
        measurePerformance(rankedSongsList);

        // read the lyrics of the songs analysed with Naive Bayes in Python
        List<Song> songsPy = SongReader.readSongsFromFile("../Lyrics_SentimentAnalysis_DataMining/pyScores.txt");

        System.out.println("\nNaive Bayes Py - Performance:");
        measurePerformance(songsPy);

        // read the lyrics of the songs analysed with Naive Bayes in Python
        List<Song> songsJava = SongReader.readSongsFromOutputFile("../Lyrics_SentimentAnalysis_DataMining/output.txt");

        System.out.println("\nNaive Bayes Java - Performance:");
        measurePerformance(songsJava);


    }

    private static void measurePerformance(List<Song> rankedSongsList) {
        // sort the songs in descending order based on their sentiment score
        Collections.sort(rankedSongsList);

        // assign relevance scores to the songs -- LINEAR RELEVANCE
        //RelevanceScorer.assignRelevanceScoresLinearly(rankedSongsList);

        // assign relevance scores to the songs -- LOGARITHMIC RELEVANCE
        RelevanceScorer.assignLogarithmicRelevanceScores(rankedSongsList);

        // Now songs are sorted by sentiment score and each song has a relevance score
        for (Song rankedSong : rankedSongsList) {
            try {
//                String fileName = "RankingAndLinearRelevance.txt"; // -- LINEAR RELEVANCE SCORE
                String fileName = "RankingAndLogarithmicRelevance.txt"; // -- LOGARITHMIC RELEVANCE SCORE
                FileWriter myWriter = new FileWriter(fileName, true);
                myWriter.write(rankedSong + "\n");
                myWriter.write("\n");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        List<Double> relevanceScores = getRelevanceScores(rankedSongsList);
        System.out.println(relevanceScores);

        // Calculate DCG (Discounted Cumulative Gain)
        double dcg = DCGCalculator.calculateDCG(relevanceScores);
        System.out.println("The DCG value is: " + dcg);

        // Calculate IDCG (Ideal Discounted Cumulative Gain)
        double idcg = IDCGCalculator.calculateIDCG(relevanceScores);
        System.out.println("The IDCG value is: " + idcg);

        // Calculate NDCG (Normalized Discounted Cumulative Gain)
        double ndcg = NDCGCalculator.calculateNDCG(dcg, idcg);
        System.out.println("The NDCG value is: " + ndcg);

        // Check if the ranking is perfect or not
        if (NDCGCalculator.isPerfectRanking(ndcg)) {
            System.out.println("The ranking is perfect!");
        } else {
            System.out.println("The ranking is not perfect...");
        }
    }

    private static List<Double> getRelevanceScores(List<Song> rankedSongsList) {
        return rankedSongsList.stream()
                .map(Song::getRelevanceScore)
                .collect(Collectors.toList());
    }
}
