package com.datamining.sentimentanalysis.v1.Classifier;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;


/**
 * The Naive Bayes Classifier Class
 *
 * Used for classifying each song out of the input dataset
 *
 */
public class NaiveBayes {
    // list of stop words
    private static Set<String> stopWords;

    // total word count seen from all the examples in the life
    // of the program
    private static int totalWordCount;

    // number of words seen in each class
    private static Map<Integer, Integer> WordCountPerClass;

    // words in each class and frequency of each word
    private static Map<Integer, Map<String, Integer>> trainingSet;

    // words that have either not been seen yet or have been seen but
    // have not yet been added to the training set (need more data before
    // deciding where the word belongs)
    private static Map<String, Map<Integer, Integer>> WordsTBD;

    // path to dataset
    private String path;

    // path to output file
    private static final String outputPath = "../Lyrics_SentimentAnalysis_DataMining/lyrics-sentimentanalysis-datamining/output.txt";

    // centralize the option to update the data set (primarily used for testing,
    // best to leave this alone)
    private boolean updatePermission;

    // http://svn.apache.org/repos/asf/lucene/java/tags/lucene_3_0_3/src/java/org/apache/lucene/analysis/PorterStemmer.java
    PorterStemmer stemmer;

    // options for the classifier
    boolean Learn;
    boolean Critique;
    boolean Stem;

    // initialize data and data structures
    public NaiveBayes(String Path, boolean learn, boolean critique, boolean stem, boolean update) {
        // String[] removeWords = {"about", "above", "across", "after", "again", "against", "all", "almost", "alone", "along", "already", "also", "although", "always", "among", "an", "and", "another", "any", "anybody", "anyone", "anything", "anywhere", "are", "area", "areas", "around", "as", "ask", "asked", "asking", "asks", "at", "away", "b", "back", "backed", "backing", "backs", "be", "became", "because", "become", "becomes", "been", "before", "began", "behind", "being", "beings", "best", "better", "between", "big", "both", "but", "by", "c", "came", "can", "cannot", "case", "cases", "certain", "certainly", "clear", "clearly", "come", "could", "d", "did", "differ", "different", "differently", "do", "does", "done", "down", "down", "downed", "downing", "downs", "during", "e", "each", "early", "either", "end", "ended", "ending", "ends", "enough", "even", "evenly", "ever", "every", "everybody", "everyone", "everything", "everywhere", "f", "face", "faces", "fact", "facts", "far", "felt", "few", "find", "finds", "first", "for", "four", "from", "full", "fully", "further", "furthered", "furthering", "furthers", "g", "gave", "general", "generally", "get", "gets", "give", "given", "gives", "go", "going", "good", "goods", "got", "great", "greater", "greatest", "group", "grouped", "grouping", "groups", "h", "had", "has", "have", "having", "he", "her", "here", "herself", "high", "high", "high", "higher", "highest", "him", "himself", "his", "how", "however", "i", "if", "important", "in", "interest", "interested", "interesting", "interests", "into", "is", "it", "its", "itself", "j", "just", "k", "keep", "keeps", "kind", "knew", "know", "known", "knows", "l", "large", "largely", "last", "later", "latest", "least", "less", "let", "lets", "like", "likely", "long", "longer", "longest", "m", "made", "make", "making", "man", "many", "may", "me", "member", "members", "men", "might", "more", "most", "mostly", "mr", "mrs", "much", "must", "my", "myself", "n", "necessary", "need", "needed", "needing", "needs", "never", "new", "new", "newer", "newest", "next", "no", "nobody", "non", "noone", "not", "nothing", "now", "nowhere", "number", "numbers", "o", "of", "off", "often", "old", "older", "oldest", "on", "once", "one", "only", "open", "opened", "opening", "opens", "or", "order", "ordered", "ordering", "orders", "other", "others", "our", "out", "over", "p", "part", "parted", "parting", "parts", "per", "perhaps", "place", "places", "point", "pointed", "pointing", "points", "possible", "present", "presented", "presenting", "presents", "problem", "problems", "put", "puts", "q", "quite", "r", "rather", "really", "right", "right", "room", "rooms", "s", "said", "same", "saw", "say", "says", "second", "seconds", "see", "seem", "seemed", "seeming", "seems", "sees", "several", "shall", "she", "should", "show", "showed", "showing", "shows", "side", "sides", "since", "small", "smaller", "smallest", "so", "some", "somebody", "someone", "something", "somewhere", "state", "states", "still", "still", "such", "sure", "t", "take", "taken", "than", "that", "the", "their", "them", "then", "there", "therefore", "these", "they", "thing", "things", "think", "thinks", "this", "those", "though", "thought", "thoughts", "three", "through", "thus", "to", "today", "together", "too", "took", "toward", "turn", "turned", "turning", "turns", "two", "u", "under", "until", "up", "upon", "us", "use", "used", "uses", "v", "very", "w", "want", "wanted", "wanting", "wants", "was", "way", "ways", "we", "well", "wells", "went", "were", "what", "when", "where", "whether", "which", "while", "who", "whole", "whose", "why", "will", "with", "within", "without", "work", "worked", "working", "works", "would", "x", "y", "year", "years", "yet", "you", "young", "younger", "youngest", "your", "yours", "z"};
        String[] removeWords = {"a","the","an"};
        stopWords = new HashSet<>(Arrays.asList(removeWords));
        totalWordCount = 0;
        WordCountPerClass = new HashMap<>();
        trainingSet = new HashMap<>();
        WordsTBD = new HashMap<>();
        path = Path;
        updatePermission = update;
        stemmer = new PorterStemmer();
        Learn = learn;
        Critique = critique;
        Stem = stem;
    }

    // takes in a path to a file and trains the Naive Bayes classifier on that data
    // Map<class, Map<word, frequency>>
    public void train() throws FileNotFoundException {
        trainingSet = createTrainingSet();
    }

    // creates a Map<class, Map<word, frequency>> that will be used for calculating probabilities in training
    private Map<Integer, Map<String, Integer>> createTrainingSet() throws FileNotFoundException {

        Map<Integer, Set<List<String>>> trainingMap = parseInput();
        Map<Integer, Map<String, Integer>> trainingSet = new HashMap<>();

        // iterate over each class
        for (Entry<Integer, Set<List<String>>> entry : trainingMap.entrySet()) {
            // classWordCount is the word count per class
            int classWordCount = 0;

            // for each class, iterate over processed string in that class
            // processed = removed stop words, symbols, spaces, etc.
            for (List<String> strings : entry.getValue()) {
                // for processed string, iterate over each word
                for (String str : strings) {
                    // if training set does not contain this class, create a new entry
                    // the new entry has the class, and map of word to word count
                    // word count is initialized to 1
                    if (trainingSet.get(entry.getKey()) == null) {
                        Map<String, Integer> temp = new HashMap<>();
                        temp.put(str, 1);
                        trainingSet.put(entry.getKey(), temp);
                    }
                    // if the class exists but does not contain a specific word
                    // add that word to the trainingSet and initialize its count to 1
                    else if (trainingSet.get(entry.getKey()).get(str) == null) {
                        Map<String, Integer> temp = trainingSet.get(entry.getKey());
                        temp.put(str, 1);
                        trainingSet.put(entry.getKey(), temp);
                    }
                    // lastly, if both the class and word exist, just increment the word's count
                    else {
                        Map<String, Integer> temp = trainingSet.get(entry.getKey());
                        temp.put(str, trainingSet.get(entry.getKey()).get(str) + 1);
                        trainingSet.put(entry.getKey(), temp);
                    }

                    // increment the total number of words seen
                    totalWordCount++;
                    // increment the total number of words seen in this class
                    classWordCount++;
                }
            }
            // maps this class to total number of words seen in this class
            WordCountPerClass.put(entry.getKey(), classWordCount);
        }

        return trainingSet;
    }

    // parses data from the input file and splits them up by class
    private Map<Integer, Set<List<String>>> parseInput() throws FileNotFoundException {
        Map<Integer, Set<List<String>>> trainingSet = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;

        try {
            // read in data from file
            while ((line = reader.readLine()) != null) {
                //get the class
                int classification = Integer.parseInt(String.valueOf(line.charAt(line.length() - 1)));

                // remove stop words, symbols, and spaces
                String[] splitWords = removeStopWords(line.replaceAll("[^a-zA-Z ]", " ").trim().split(" "));
                List<String> words = Arrays.asList(splitWords);

                // create a new Set of processed strings
                // processed = removed stop words, symbols, spaces, etc.
                Set<List<String>> set = new HashSet<>();

                // if we see that there is already a set available
                // for this class, we overwrite our previously created set
                if (trainingSet.get(classification) != null)
                    set = trainingSet.get(classification);

                // add word to set and put it back into trainingSet
                set.add(words);
                trainingSet.put(classification, set);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return trainingSet;
    }

    // removes all the stop words from a given set of words
    private String[] removeStopWords(String[] strings) {
        int count = 0;
        for (int i = 0; i < strings.length; i++)
            if (!stopWords.contains(strings[i]) && strings[i].intern().trim() != "")
                count++;

        String[] newArray = new String[count];
        for (int i = 0, tracker = 0; i < strings.length && tracker < count; i++)
            if (!stopWords.contains(strings[i]) && strings[i].intern().trim() != "") {
                if (Stem)
                    newArray[tracker] = stemmer.stem(strings[i]);
                else
                    newArray[tracker] = strings[i];
                tracker++;
            }

        return newArray;
    }

    // given that the classification is at least 90% certain, we either update the count
    // in the training set (if the word exists in the training set), or we watch the word
    // until we decide the correct classification for the word.
    private void updateTrainingSet(double probability, int classification, String[] words) throws IOException {
        // prune classifications that the classifier is not at least 90% certain about

        if (Learn) {
            int count = 0;
            if (!Critique) {

                // iterate for each word in my string
                for (String word : words) {
                    if (trainingSet.get(classification).get(word) != null) {
                        trainingSet.get(classification).put(word, trainingSet.get(classification).get(word) + 1);
                    } else {
                        Map<String, Integer> temp = trainingSet.get(classification);
                        temp.put(word, 1);
                        trainingSet.put(classification, temp);
                    }

                    totalWordCount++;
                    count++;
                }
            } else if (probability >= .9) {
                for (String word : words) {
                    // get the words being monitored
                    Map<Integer, Integer> temp = WordsTBD.get(word);

                    // if word exists in trainingSet, update its frequency
                    if (trainingSet.get(classification).get(word) != null) {
                        trainingSet.get(classification).put(word, trainingSet.get(classification).get(word) + 1);
                        totalWordCount++;
                        WordCountPerClass.put(classification, WordCountPerClass.get(classification) + 1);
                        count++;
                    }

                    // if word is being watched
                    else if (temp != null) {
                        // but the word has not been classified for this class before
                        // create the count for this class
                        if (temp.get(classification) == null) {
                            temp.put(classification, 1);
                            WordsTBD.put(word, temp);
                        }

                        // if this word has been seen at least 500 times for this specific classification
                        // put it in the training set under that classification, then remove it from the
                        // list of words being watched. Otherwise, increment the count
                        else {
                            temp.put(classification, temp.get(classification) + 1);
                            if (temp.get(classification) >= 1000) {
                                trainingSet.get(classification).put(word, temp.get(classification));
                                WordsTBD.remove(word);
                            }
                        }
                    }

                    // if the word is not currently being watched
                    // add the word to the list of words being watched
                    else if (temp == null) {
                        temp = new HashMap<>();
                        temp.put(classification, 1);
                        WordsTBD.put(word, temp);
                    }
                }
            }


            // only update the data set if the classifier knows every word in the sentence
            // and is positive of each of their classifications
            if (count == words.length && updatePermission)
                updateDataSet(classification, words);
        }
    }

    // adds examples seen to our data set, so that we never lose our progress
    private void updateDataSet(int classification, String[] words) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath, true));
        String classificationName= "";

        // add words to file
        for (String word : words) {
            bw.write(word + " ");
        }

        if (classification == -5){
            classificationName = "End of the Song";
        }
        else if (classification == -1) {
            classificationName = "Very negative";
        }
        else if (classification == 0) {
            classificationName = "Negative";
        }
        else if (classification == 1) {
            classificationName = "Neutral";
        }
        else if (classification == 2) {
            classificationName = "Positive";
        }
        else if (classification == 3) {
            classificationName = "Very positive";
        };

        bw.write("\n ~~~~~~~~~~~~~~~~~~~ SONG SENTIMENT ~~~~~~~~~~~~~~~~~~~ " + classificationName + " " + classification + "\t");

        // create new line and close file
        bw.newLine();
        bw.flush();
        bw.close();
    }

    // given a path to a file, classify each line of text in the file
    public Map<String, Integer> classifyFile(String Path)
    {
        Map<String, Integer> classifications = new HashMap<>();
        String line;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(Path));

            // read in data from file
            while ((line = reader.readLine()) != null)
            {
                // classify a line of text
                int n = analyze(line.replaceAll("[^a-zA-Z ]", " ").trim());

                // add classification to map to return to user
                classifications.put(line, n);
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classifications;
    }

    // given a path to a file, evaluate the classifier's accuracy
    public Double evaluate(String Path) throws IOException {
        Map<Integer, Set<List<String>>> Wrong = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(Path));
        String line;
        Double correct = 0.0;
        Double total = 0.0;

        try {
            // read in data from file
            while ((line = reader.readLine()) != null) {
                // get the class
                int classification = Integer.parseInt(String.valueOf(line.charAt(0)));

                // remove stop words, symbols, and spaces
                int classified = analyze(line.replaceAll("[^a-zA-Z ]", " ").trim());

                if (classified == classification)
                    correct++;
                else {
                    if (Wrong.get(classification) == null) {
                        Set<List<String>> sentence = new HashSet<>();
                        List<String> strings = new ArrayList<>();
                        strings.add(line);
                        sentence.add(strings);
                        Wrong.put(classification, sentence);
                    } else if (Wrong.get(classification) != null) {
                        Set<List<String>> sentence = Wrong.get(classification);
                        List<String> strings = new ArrayList<>();
                        strings.add(line);
                        sentence.add(strings);
                        Wrong.put(classification, sentence);
                    }
                }
                total++; 
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return correct / total;
    }

    // given a string, tell the user what class it belongs to
    private Integer analyze(String str) throws IOException {
        // base case: if string is empty, then no classification
        if (str.intern().trim() == "") return 1;

        // remove stop words, symbols, and spaces
        String[] splitWords = removeStopWords(str.trim().replaceAll("[^a-zA-Z ]", " ").split(" "));

        // probability of each word occurring for each class
        // Map<class, Map<word, probability>>
        Map<Integer, Map<String, Double>> probabilities = new HashMap<>();

        // probability of each class occurring
        // Map<class, probability>
        Map<Integer, Double> probabilityOfClass = new HashMap<>();

        // compute probability of given word for each class
        for (Entry<Integer, Map<String, Integer>> entry : trainingSet.entrySet()) {
            // total number of words in this class
            int totalClassWordCount = WordCountPerClass.get(entry.getKey());

            // probability of this class occurring
            probabilityOfClass.put(entry.getKey(), (double) totalClassWordCount / totalWordCount);

            // iterate over each word in input
            for (String word : splitWords) {
                // if we have this word in our training set, compute its probability
                if (trainingSet.get(entry.getKey()).get(word) != null) {
                    // if nothing has been added for this class, initialize
                    // the map for this class
                    if (probabilities.get(entry.getKey()) == null)
                        probabilities.put(entry.getKey(), new HashMap<>());

                    // if the word exists, double its probability
                    if (probabilities.get(entry.getKey()).get(word) != null)
                        probabilities.get(entry.getKey()).put(word, (2 * probabilities.get(entry.getKey()).get(word)));

                        // if the word does not exist, compute its probability
                    else {
                        // probability = probability of seeing word in this class / word count in this class
                        Double prob = (double) (((entry.getValue().get(word) == null) ? 1 : entry.getValue().get(word)) / ((WordCountPerClass.get(word) == null) ? 1 : WordCountPerClass.get(word)));
                        Map<String, Double> probabilityForWord = new HashMap<>();
                        probabilityForWord.put(word, prob);
                        probabilities.put(entry.getKey(), probabilityForWord);
                    }
                }
            }
        }

        // class is positive by default
        Integer determinedClass = 1;
        Map<Integer, Double> maxProbabilities = new HashMap<>();
        Double totalProbability = 0.0;

        // now that the probabilities have been computed for each word.
        // compute the probability for the string as a whole
        for (Entry<Integer, Map<String, Double>> entry : probabilities.entrySet()) {
            // running product
            double currentProbability = probabilityOfClass.get(entry.getKey());
            for (Entry<String, Double> weights : entry.getValue().entrySet())
                currentProbability *= weights.getValue();

            // calculate the total probability for a given input
            totalProbability += currentProbability;

            // store the probability for each class
            maxProbabilities.put(entry.getKey(), currentProbability);
        }

        // finds the max normalized probability and corresponding class
        double max = Double.MIN_VALUE;
        for (Entry<Integer, Double> entry : maxProbabilities.entrySet()) {
            if (entry.getValue() / totalProbability > max) {
                max = entry.getValue() / totalProbability;
                determinedClass = entry.getKey();
            }
        }

        // update my training set
        updateTrainingSet(max, determinedClass, splitWords);

        // default is positive, but classifying the string
        // as positive doesn't necessarily teach the classifier
        // that the string is positive.
        return determinedClass;
    }

    // tells us what class an input string belongs in
    public Integer classify(String str) throws IOException {
        return analyze(str);
    }
}
