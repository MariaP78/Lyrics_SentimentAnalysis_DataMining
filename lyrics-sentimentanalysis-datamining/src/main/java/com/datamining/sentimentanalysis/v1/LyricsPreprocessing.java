package com.datamining.sentimentanalysis.v1;

import java.io.*;
import java.nio.file.*;

public class LyricsPreprocessing {
    public static void main(String[] args) {
        String inputFilePath = "../Lyrics_SentimentAnalysis_DataMining/Dataset_Songs_Lyrics.txt";
        String outputFilePath = "../Lyrics_SentimentAnalysis_DataMining/lyrics-sentimentanalysis-datamining/src/main/java/com/datamining/sentimentanalysis/v1/Preprocessed_Songs_Lyrics.txt"; // Replace with desired output file path

        try {
            preprocessLyrics(inputFilePath, outputFilePath);
            System.out.println("Preprocessing completed. Preprocessed file saved to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error occurred during file processing: " + e.getMessage());
        }
    }

    private static void preprocessLyrics(String inputFilePath, String outputFilePath) throws IOException {
        Path inputFile = Paths.get(inputFilePath);
        Path outputFile = Paths.get(outputFilePath);

        try (BufferedReader reader = Files.newBufferedReader(inputFile);
             BufferedWriter writer = Files.newBufferedWriter(outputFile)) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                String processedLine = line.toLowerCase().replaceAll("[^a-z\\s]", "");
                writer.write(processedLine);
                writer.newLine();
            }
        }
    }
}

