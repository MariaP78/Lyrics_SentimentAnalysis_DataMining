package com.datamining.sentimentanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LyricsReader {
    public static void main(String[] args) {
        String filePath = "../Lyrics_SentimentAnalysis_DataMining//Dataset_Songs_Lyrics.txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line as required
                System.out.println(line); // Prints each line of the file
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions here
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

