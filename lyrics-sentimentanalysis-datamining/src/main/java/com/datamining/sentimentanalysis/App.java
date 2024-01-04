package com.datamining.sentimentanalysis;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Lyrics Sentiment Analysis Data Mining Main Class
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "This is the main class!" );
        String filePath = "../Lyrics_SentimentAnalysis_DataMining//Dataset_Songs_Lyrics.txt";
        List<String> songs = LyricsReader.readLyrics(filePath);

        // use the SentimentAnalyzer class to analyze the sentiment of each song
        SentimentAnalyzer sentimentAnalyzer = new SentimentAnalyzer();
        sentimentAnalyzer.analyzeSentiment(songs);

        // SONGS RANKING based on their sentiment from -1 to 3
        // Very Negative = -1.0
        // Negative = 0.0
        // Neutral = 1.0
        // Positive = 2.0
        // Very Positive = 3.0

        List<Song> rankedSongsList = sentimentAnalyzer.rankedSongs;
        // sort the songs in descending order based on their sentiment score
        Collections.sort(rankedSongsList);

        // assign relevance scores to the songs linearly
        // RelevanceScorer.assignRelevanceScoresLinearly(rankedSongsList);

        // assign relevance scores to the songs logarithmically
        RelevanceScorer.assignLogarithmicRelevanceScores(rankedSongsList);

        // // Now songs are sorted by sentiment score and each song has a relevance score
        // for (Song rankedSong : rankedSongsList) {
        //     // System.out.println(rankedSong);
        //     try {
        //             FileWriter myWriter = new FileWriter("RankingAndLinearRelevance.txt", true);
        //             myWriter.write(rankedSong + "\n");
        //             myWriter.write("\n");
        //             myWriter.close();
        //         } catch (IOException e) {
        //             System.out.println("An error occurred.");
        //             e.printStackTrace();
        //         }
        // }

        // Now songs are sorted by sentiment score and each song has a relevance score
        for (Song rankedSong : rankedSongsList) {
            // System.out.println(rankedSong);
            try {
                    FileWriter myWriter = new FileWriter("RankingAndLogarithmicRelevance.txt", true);
                    myWriter.write(rankedSong + "\n");
                    myWriter.write("\n");
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
        }
    }
}
