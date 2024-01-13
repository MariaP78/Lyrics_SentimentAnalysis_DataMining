package com.datamining.sentimentanalysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Song Reader Class
 * <p>
 * Used for reading the analysed songs by Naive Bayes from pyScores.txt and output.txt
 */
public class SongReader {

    public static List<Song> readSongsFromFile(String filePath) {
        List<Song> songs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Song currentSong = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Lyrics:")) {
                    currentSong = new Song();
                    currentSong.setLyrics(line.substring("Lyrics:".length()).trim());
                } else if (line.startsWith("Score:")) {
                    if (currentSong != null) {
                        currentSong.setSentimentScore(Double.parseDouble(line.substring("Score:".length()).trim()));
                    }
                } else if (line.startsWith("RelevanceScore:")) {
                    if (currentSong != null) {
                        currentSong.setRelevanceScore(Double.parseDouble(line.substring("RelevanceScore:".length()).trim()));
                        songs.add(currentSong);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return songs;
    }

    public static List<Song> readSongsFromOutputFile(String filePath) {
        List<Song> songs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            Song currentSong = null;

            while ((line = reader.readLine()) != null) {
                if (!(line.contains("~~~~~~~~~~~~~~~~~~~ SONG SENTIMENT ~~~~~~~~~~~~~~~~~~~"))) {
                    currentSong = new Song();
                    currentSong.setLyrics(line);
                } else {
                    if (currentSong != null) {
                        currentSong.setSentimentScore(Double.parseDouble(line.substring(line.length() - 2).trim()));
                    }
                    songs.add(currentSong);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return songs;
    }

}
