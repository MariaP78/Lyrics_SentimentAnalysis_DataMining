package com.datamining.sentimentanalysis;

import edu.stanford.nlp.pipeline.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SentimentAnalyzer {
    public List<Song> rankedSongs = new ArrayList<>();
    // Analyzes the sentiment of each song in the list
    public void analyzeSentiment(List<String> songs) {
        // Create a StanfordCoreNLP object with the properties for sentiment analysis
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // ranking the songs based on their sentiment from -1 to 3
        double rank = 0.0;
        

        // Iterate over the songs in the list
        for (String song : songs) {
            // Annotate the song
            CoreDocument document = new CoreDocument(song);
            pipeline.annotate(document);

            // Iterate over the sentences in the document
            for (CoreSentence sentence : document.sentences()) {
                String sentiment = sentence.sentiment();
                if (sentiment.equals("Very negative")) {
                        rank = -1.0;
                    }
                    else if (sentiment.equals("Negative")) {
                        rank = 0.0;
                    }
                    else if (sentiment.equals("Neutral")) {
                        rank = 1.0;
                    }
                    else if (sentiment.equals("Positive")) {
                        rank = 2.0;
                    }
                    else if (sentiment.equals("Very positive")) {
                        rank = 3.0;
                    }
                    else {
                        rank = 0.0;
                    }
                rankedSongs.add(new Song(sentence.toString(), rank));
                
                // System.out.println(sentence + " \n~~~~~~~~~~~~~~~~~~~ SONG SENTIMENT ~~~~~~~~~~~~~~~~~~~ " + sentiment + "\n");
                // writing everything to a file without overwriting
                try {
                    FileWriter myWriter = new FileWriter("LyricsSentimentAnalysis.txt", true);
                    myWriter.write(sentence + " \n~~~~~~~~~~~~~~~~~~~ SONG SENTIMENT ~~~~~~~~~~~~~~~~~~~ " + sentiment + " " + rank + "\n");
                    myWriter.write("\n");
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

            }
        }
        System.out.println("Successfully wrote to the file.");
    }

    public static void main(String[] args) {
        // Create a StanfordCoreNLP object with the properties for sentiment analysis
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String textPositive= "Dancing Symphony\n" + //
        "Like a lighthouse in the storm, your love is my guide\n" + //
        "Our love's a melody that echoes through the night\n" + //
        "Our story is written in the stars above\n" + //
        "Just like the stars belong to the night\n" + //
        "Every heartbeat calls your name in a whisper\n" + //
        "In your eyes, I find my tomorrow\n" + //
        "Hand in hand, heart to heart, our souls take flight\n" + //
        "In the symphony of life, your heart's my favorite note";

        // Annotate the text
        CoreDocument document = new CoreDocument(textPositive);
        pipeline.annotate(document);

        // Iterate over the sentences in the document
        for (CoreSentence sentence : document.sentences()) {
            String sentiment = sentence.sentiment();
            System.out.println(sentence + " \n~~~~~~~~~~~~~~~~~~~ LYRICS SENTIMENT ~~~~~~~~~~~~~~~~~~~ " + sentiment);
        }
    }
}