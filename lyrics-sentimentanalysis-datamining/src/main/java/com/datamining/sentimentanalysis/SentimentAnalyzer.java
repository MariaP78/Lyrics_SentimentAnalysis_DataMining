package com.datamining.sentimentanalysis;

import edu.stanford.nlp.pipeline.*;
import java.util.*;

public class SentimentAnalyzer {
    public static void main(String[] args) {
        // Create a StanfordCoreNLP object with the properties for sentiment analysis
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Example text (replace with your text)
        // String textPositive= "Dancing Symphony\n" + //
        // "Like a lighthouse in the storm, your love is my guide\n" + //
        // "Our love's a melody that echoes through the night\n" + //
        // "Our story is written in the stars above\n" + //
        // "Just like the stars belong to the night\n" + //
        // "Every heartbeat calls your name in a whisper\n" + //
        // "In your eyes, I find my tomorrow\n" + //
        // "Hand in hand, heart to heart, our souls take flight\n" + //
        // "In the symphony of life, your heart's my favorite note";

        // String textNegative= "Eternal Rain\n" + //
        // "Now I walk this path alone, where once we walked together\n" + //
        // "Love's tender flame has faded into the cold shadow of the past\n" + //
        // "In the garden of our love, the roses wilt and wither\n" + //
        // "Tears fall silently, blurring yesterday's joy\n" + //
        // "Your absence is a silent scream that echoes within\n" + //
        // "Echoes of your voice haunt my dreams\n" + //
        // "A single picture, a thousand shattered memories\n" + //
        // "Time stands still in the room where we laughed";

        String textNeutral = "Singing in the rain\n" + //
        "I'm singing in the rain\n" + //
        "I'm walking down the street, on a random day\n" + //
        "The sky is blue, and the trees are swaying";

        // Annotate the text
        CoreDocument document = new CoreDocument(textNeutral);
        pipeline.annotate(document);

        // Iterate over the sentences in the document
        for (CoreSentence sentence : document.sentences()) {
            String sentiment = sentence.sentiment();
            System.out.println(sentence + " \n~~~~~~~~~~~~~~~~~~~ LYRICS SENTIMENT ~~~~~~~~~~~~~~~~~~~ " + sentiment);
        }
    }
}