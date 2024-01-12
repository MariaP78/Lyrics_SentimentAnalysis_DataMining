// LyricsAnalyzer.js
import React, { useState } from "react";
import axios from "axios";
import "./LyricsAnalyzer.css";

const LyricsAnalyzer = () => {
  const [lyrics, setLyrics] = useState("");
  const [sentiment, setSentiment] = useState("");
  const [loading, setLoading] = useState(false);
  const analyzeSentiment = async () => {
    try {
      setLoading(true);
      const response = await axios.post(
        "http://localhost:8080/sentiment-analysis",
        { lyrics }
      );
      setSentiment(response.data.sentiment);
      console.log("Sentiment endpoint analyzed");
    } catch (error) {
      console.error("Error analyzing sentiment:", error);
    } finally {
      setLoading(false);
    }
  };
  const getSentimentColor = (sentiment) => {
    switch (sentiment) {
      case "Very positive":
        return "green";
      case "Positive":
        return "#74bc11";
      case "Neutral":
        return "#4d8feb";
      case "Negative":
        return "#ff6464";
      case "Very negative":
        return "red";
      default:
        return "black";
    }
  };

  return (
    <div className="app-container">
      <div className="content-container">
        <h1 className="app-title">🎵 Lyrics Sentiment Analyzer 🎵</h1>

        <textarea
          className="lyrics-input"
          placeholder="Enter lyrics here..."
          value={lyrics}
          onChange={(e) => setLyrics(e.target.value)}
        ></textarea>

        <button
          className={`analyze-button${loading ? " disabled-button" : ""}`}
          onClick={analyzeSentiment}
          disabled={loading}
        >
          Analyze Sentiment
        </button>

        {sentiment && (
          <div className="sentiment-result">
            <p className="result-label">Sentiment:</p>
            {loading ? (
              <p className={`result-text`}>Loading...</p>
            ) : (
              <p
                style={{ color: getSentimentColor(sentiment) }}
                className={`result-text`}
              >
                {sentiment.toUpperCase()}
              </p>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default LyricsAnalyzer;
