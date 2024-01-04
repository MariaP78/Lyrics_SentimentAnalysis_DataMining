package com.datamining.sentimentanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LyricsReader {

    public static List<String> readLyrics(String filePath) {
        List<String> songs = new ArrayList<>();
        StringBuilder songBuilder = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    // Check if the next line is also empty (indicating end of a song)
                    reader.mark(1000); // Marking the current position
                    String nextLine = reader.readLine();
                    if (nextLine != null && nextLine.trim().isEmpty()) {
                        songs.add(songBuilder.toString().trim());
                        songBuilder = new StringBuilder(); // Reset for next song
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