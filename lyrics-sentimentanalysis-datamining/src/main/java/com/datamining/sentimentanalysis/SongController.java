package com.datamining.sentimentanalysis;

import org.springframework.web.bind.annotation.*;
import edu.stanford.nlp.pipeline.*;
import java.util.*;

/**
 * The Song Controller Class
 * 
 * creates the endpoint for the sentiment analysis of a song
 * 
 */
@RestController
public class SongController {
    // function that analyzes the sentiment of a single song
    private static String analyzeSentimentSingleSong(String song) {
        // Create a StanfordCoreNLP object with the properties for sentiment analysis
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Annotate the song
        CoreDocument document = new CoreDocument(song);
        pipeline.annotate(document);

        // Iterate over the sentences in the document
        for (CoreSentence sentence : document.sentences()) {
            String sentiment = sentence.sentiment();
            return sentiment;
        }
        return "No sentiment";
    }

    // Endpoint for the sentiment analysis of a song
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/sentiment-analysis")
    public SongResponse analyzeSentimentEndpoint(@RequestBody SongRequest songRequest) {
        // return the sentiment of the song
        System.out.println("Request successfully sent!");
        return new SongResponse(analyzeSentimentSingleSong(songRequest.getLyrics()));
    }
}
