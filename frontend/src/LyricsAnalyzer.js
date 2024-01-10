// LyricsAnalyzer.js
import React, { useState } from "react";
import axios from "axios";
import "./LyricsAnalyzer.css";

const LyricsAnalyzer = () => {
  const [lyrics, setLyrics] = useState("");
  const [sentiment, setSentiment] = useState("");

  const analyzeSentiment = async () => {
    try {
      // Replace 'YOUR_BACKEND_API_ENDPOINT' with the actual endpoint
      // const response = await axios.post('YOUR_BACKEND_API_ENDPOINT', { lyrics });
      // setSentiment(response.data.sentiment);
      console.log("Sentiment endpoint analyzed");
    } catch (error) {
      console.error("Error analyzing sentiment:", error);
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

        <button className="analyze-button" onClick={analyzeSentiment}>
          Analyze Sentiment
        </button>

        {sentiment && (
          <div className="sentiment-result">
            <p className="result-label">Sentiment:</p>
            <p
              className={`result-text ${
                sentiment === "positive" ? "positive" : "negative"
              }`}
            >
              {sentiment.toUpperCase()}
            </p>
          </div>
        )}
      </div>
    </div>
  );
};

export default LyricsAnalyzer;
