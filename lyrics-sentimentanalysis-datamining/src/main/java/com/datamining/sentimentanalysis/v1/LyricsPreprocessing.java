package com.datamining.sentimentanalysis.v1;

import java.io.*;
import java.nio.file.*;

/**
 * The Lyrics Preprocessing Class
 * 
 * Used for preprocessing the lyrics of the songs from our dataset Dataset_Songs_Lyrics.txt
 *
 */
public class LyricsPreprocessing {
    public static void main(String[] args) {
        String inputFilePath = "../Lyrics_SentimentAnalysis_DataMining/Dataset_Songs_Lyrics.txt";
        String outputFilePath = "../Lyrics_SentimentAnalysis_DataMining/lyrics-sentimentanalysis-datamining/src/main/java/com/datamining/sentimentanalysis/v1/Preprocessed_Songs_Lyrics.txt";

        try {
            preprocessLyrics(inputFilePath, outputFilePath);
            System.out.println("Preprocessing completed. Preprocessed file saved to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error occurred during file processing: " + e.getMessage());
        }
    }

    // Preprocess the lyrics of the songs from our dataset
    private static void preprocessLyrics(String inputFilePath, String outputFilePath) throws IOException {
        Path inputFile = Paths.get(inputFilePath);
        Path outputFile = Paths.get(outputFilePath);

        try (BufferedReader reader = Files.newBufferedReader(inputFile);
             BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                // Preprocess (lowercase and remove punctuation) for each line
                String processedLine = line.toLowerCase().replaceAll("[^a-z\\s]", "");
                writer.write(processedLine);
                writer.newLine();
            }
        }
    }
}

