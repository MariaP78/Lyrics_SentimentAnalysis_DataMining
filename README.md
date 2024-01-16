# Lyrics_SentimentAnalysis_DataMining

We will perform sentiment analysis on song lyrics. We are going to analyse the emotional tone of lyrics across different genres or time periods.
Performing sentiment analysis on song lyrics involves evaluating the emotional tone expressed in the lyrics of songs. With our project we will be able to classify the lyrics as (very) positive, (very) negative or neutral, indicating the emotional sentiment behind the words.

## Description

In this README file, you will be able to find information regarding how to compile and run the project.

## Stanford Implementation

Before starting the Java Main Class (App.java), make sure you have **Java** and **Maven** installed on your computer and then run _mvn clean_ and _mvn install_ to build the project.
Run the App.java class. After this, the Sentiment Analysis will be performed, the output will be available in the LyricsSentimentAnalysis.txt file and the performance measurements with DCG, IDCG and NDCG will be computed and will appear in the console.

When Measuring the performance of the algorithm from Python, before running the main App.java, you need to access the endpoint GET http://localhost:8080/python_bayes_classification. (Make sure the Spring App - SpringBootApp.java is running)

## Naive Bayes Java Implementation

Before starting the Java Main Class (BayesMain.java), make sure you have **Java** and **Maven** installed on your computer and then run _mvn clean_ and _mvn install_ to build the project.
Go to Lyrics_SentimentAnalysis_DataMining/lyrics-sentimentanalysis-datamining/src/main/java/com/datamining/sentimentanalysis/v1/Classifier folder and run the BayesMain.java class. After this, the output with the analysed songs will be available in the output.txt file.

## Naive Bayes Python Implementation

Before running the main.py file, make sure you have **Python** installed on your computer and all the necessary dependencies installed. In order to install the dependencies, make sure you are in the Lyrics/SentimentAnalysis/DataMining/python-endpoint folder and run _pip install dependencyname_ for each of the following: json, flask, re, nltk, pandas and scikit-learn in your terminal window. Run the main.py file and the result will be available in the output.txt from python_endpoint folder.

## Frontend

Before starting the REACT Frontend locally, make sure you have **Node** installed on your computer and the **Spring Boot App running** in the background (this can be done by running SpringBootApp.java) and then, please run _npm install_ to install all the necessary dependencies and then _npm start_ in Terminal and make sure to be in the frontend folder (Lyrics_SentimentAnalysis_DataMining/frontend). This will start the Frontend app, which will be available at http://localhost:3000/. After that, you can introduce a song in the available input field and then click the **Analyze Sentiment** button to perform the song's lyrics analysis.
