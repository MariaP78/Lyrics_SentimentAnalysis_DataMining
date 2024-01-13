package com.datamining.sentimentanalysis;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PythonResponse {
    private String message;
    private List<ResultItem> result;


    public static class ResultItem {
        @JsonProperty("Sentiment")
        private String sentiment;
        @JsonProperty("Song")
        private String song;
        @JsonProperty("Value")
        private String value;

        public String getSentiment() {
            return sentiment;
        }

        public void setSentiment(String sentiment) {
            this.sentiment = sentiment;
        }

        public String getSong() {
            return song;
        }

        public void setSong(String song) {
            this.song = song;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
