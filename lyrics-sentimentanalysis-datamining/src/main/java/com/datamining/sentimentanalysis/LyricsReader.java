package com.datamining.sentimentanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Lyrics Reader Class
 * 
 * Used for reading the lyrics of the songs from our dataset Dataset_Songs_Lyrics.txt
 *
 */
public class LyricsReader {

    public static List<String> readLyrics(String filePath) {
        // Read the lyrics of the songs from the dataset
        List<String> songs = new ArrayList<>();
        StringBuilder songBuilder = new StringBuilder();
        BufferedReader reader = null;

        try {
            // Read the file line by line
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    // Check if the next line is also empty (indicating end of a song)
                    reader.mark(1000); // Marking the current position
                    String nextLine = reader.readLine();
                    if (nextLine != null && nextLine.trim().isEmpty()) {
                        songs.add(songBuilder.toString().trim());
                        songBuilder = new StringBuilder(); // Reset the song builder to start building the next song
                    } else {
                        songBuilder.append(line).append("\n");
                    }
                    if (nextLine != null) {
                        reader.reset(); // Reset back to the marked position
                    }
                } else {
                    songBuilder.append(line).append("\n");
                }
            }
            // Add last song if file doesn't end with two blank lines
            if (!songBuilder.toString().trim().isEmpty()) {
                songs.add(songBuilder.toString().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the reader
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return songs;
    }
}
