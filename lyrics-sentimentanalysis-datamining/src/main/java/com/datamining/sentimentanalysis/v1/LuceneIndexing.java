package com.datamining.sentimentanalysis.v1;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import java.nio.file.Paths;

public class LuceneIndexing {
    public static void main(String[] args) {
        String indexPath = "../Lyrics_SentimentAnalysis_DataMining/lyrics-sentimentanalysis-datamining/src/main/java/com/datamining/sentimentanalysis/v1/index";
        try {
            // Set up Lucene indexer
            Directory dir = FSDirectory.open(Paths.get(indexPath));
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(dir, config);

            // Sample lyrics data
            String[] lyrics = {"dancing symphony\n" + //
                    "like a lighthouse in the storm your love is my guide\n" + //
                    "our loves a melody that echoes through the night\n" + //
                    "our story is written in the stars above\n" + //
                    "just like the stars belong to the night\n" + //
                    "every heartbeat calls your name in a whisper\n" + //
                    "in your eyes i find my tomorrow\n" + //
                    "hand in hand heart to heart our souls take flight\n" + //
                    "in the symphony of life your hearts my favorite note", 
                    "fading passion\n" + //
                    "in the symphony of life your hearts my favorite note\n" + //
                    "just like the stars belong to the night\n" + //
                    "our story is written in the stars above\n" + //
                    "hand in hand heart to heart our souls take flight\n" + //
                    "our loves a melody that echoes through the night\n" + //
                    "like a lighthouse in the storm your love is my guide\n" + //
                    "every heartbeat calls your name in a whisper\n" + //
                    "in your eyes i find my tomorrow"};

            // Indexing documents
            for (String text : lyrics) {
                Document doc = new Document();
                doc.add(new TextField("lyrics", text, Field.Store.YES));
                writer.addDocument(doc);
            }
            writer.close();

            // Using QueryParser for searching
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);

            // Create a QueryParser
            QueryParser parser = new QueryParser("lyrics", analyzer);
            Query query = parser.parse("love");

            TopDocs results = searcher.search(query, 10);

            // ... Process search results ...
            ScoreDoc[] analysedLyrics = results.scoreDocs;
            System.out.println("Found " + analysedLyrics.length + " songs.");
            for (int i=0; i<analysedLyrics.length; ++i) {
                Document doc = searcher.doc(analysedLyrics[i].doc);
                System.out.println((i + 1) + ". " + doc.get("lyrics"));
            }

            reader.close();
            dir.close();

        } catch (Exception e) {
            System.err.println("Exception caught: " + e.getMessage());
        }
    }
}
