package com.datamining.sentimentanalysis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.datamining.sentimentanalysis.PythonResponse.ResultItem;

/**
 * The Song Controller Class
 * <p>
 * creates the endpoint for the sentiment analysis of a song
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

    // Endpoint transferring data from Python
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/python_bayes_classification")
    public void receiveAnalyseMadeWithPy() throws JsonProcessingException {

        String pythonEndpoint = "http://127.0.0.1:5000/python_bayes_classification";

        // Create the RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Make the GET request
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                pythonEndpoint,
                HttpMethod.GET,
                null,
                String.class
        );

        //deserialize data
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
        String message = jsonNode.get("message").asText();
        ResultItem[] result = objectMapper.readValue(jsonNode.get("result").toString(), ResultItem[].class);

        List<Song> pyList = new ArrayList<>();
        for (ResultItem resultItem : result) {
            Song song = new Song(resultItem.getSong(), Double.parseDouble(resultItem.getValue()));
            pyList.add(song);
        }

        for (Song song : pyList) {
            try {
                FileWriter myWriter = new FileWriter("pyScores.txt", true);
                myWriter.write(song + "\n");
                myWriter.write("\n");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
