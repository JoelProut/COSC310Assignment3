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

public class CoreNLP {
	// The CoreNLP class will take in the user input and return: Named entity
	// recognition and POS tagging depending on the method.
	private Properties props;
	private StanfordCoreNLP pipeline;

	public CoreNLP() {
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, sentiment, dcoref");
		pipeline = new StanfordCoreNLP(props);
		
	}
	
	public ArrayList<String> getWords(String input) {
		Annotation document = new Annotation(input); // For annotation
		ArrayList<String> words = new ArrayList<String>();
		pipeline.annotate(document);
		// POS and named entity
		List<CoreMap> sentencesAnnotated = document.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentencesAnnotated) {
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				words.add(token.getString(CoreAnnotations.OriginalTextAnnotation.class));
			}
		}
		return words;

	}
	
	public ArrayList<String> getPOS(String input) {
		Annotation document = new Annotation(input); // For annotation
		ArrayList<String> partOfSpeach = new ArrayList<String>();
		pipeline.annotate(document);
		// POS and named entity
		List<CoreMap> sentencesAnnotated = document.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentencesAnnotated) {
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				partOfSpeach.add(token.get(CoreAnnotations.PartOfSpeechAnnotation.class));
			}
		}
		return partOfSpeach;

	}
	
	public ArrayList<String> getNER(String input) {
		Annotation document = new Annotation(input); // For annotation
		ArrayList<String> namedEntity = new ArrayList<String>();
		pipeline.annotate(document);
		// POS and named entity
		List<CoreMap> sentencesAnnotated = document.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentencesAnnotated) {
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				namedEntity.add(token.getString(CoreAnnotations.CoarseNamedEntityTagAnnotation.class));
			}
		}
		return namedEntity;

	}
	
	public int getSentiment(String input) { // 1 = negative, 2 = neutral, 3 = positive
		Annotation annotation = pipeline.process(input);
		for(CoreMap sentence: annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
			Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
			return RNNCoreAnnotations.getPredictedClass(tree);
		}
		return -1;
	}
	
	public static void main(String[] args) {
		String input = "I despise Hitler.";
		CoreNLP coreNLP = new CoreNLP();
		ArrayList<String> words = coreNLP.getWords(input);
		ArrayList<String> pos = coreNLP.getPOS(input);
		ArrayList<String> ne = coreNLP.getNER(input);
		int i = coreNLP.getSentiment(input);
		System.out.println(words);
		System.out.println(pos);
		System.out.println(ne);
		System.out.println(i);
	}
}
