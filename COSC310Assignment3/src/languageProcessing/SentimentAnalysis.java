package languageProcessing;

import java.io.*;
import java.util.*;
import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class SentimentAnalysis {
	private Properties props;
	private StanfordCoreNLP pipeline;
	
	public SentimentAnalysis() {
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		pipeline = new StanfordCoreNLP(props);
	}
	
	public double getSentiment(String input) { // 0 = strongly negative, 4 = strongly positive
		Annotation annotation = pipeline.process(input);
		for(CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
			return RNNCoreAnnotations.getPredictedClass(tree);
		}
		return -1;
	}
	
	public static void main(String[] args) {
		SentimentAnalysis sentiment = new SentimentAnalysis();
		double i = sentiment.getSentiment("I really love cheesies.");
		System.out.println(i);
	}

}
