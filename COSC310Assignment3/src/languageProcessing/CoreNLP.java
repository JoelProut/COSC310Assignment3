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
	public ArrayList<String> words;
	public ArrayList<String> pos;
	public ArrayList<String> ner;
	public ArrayList<String> lemma;
	public int sentiment;

	public CoreNLP() {
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, sentiment, dcoref");
		pipeline = new StanfordCoreNLP(props);
		
	}
	
	public void annotate(String input) {
		words = new ArrayList<String>();
		pos = new ArrayList<String>();
		ner = new ArrayList<String>();
		lemma = new ArrayList<String>();
		Annotation document = new Annotation(input);
		pipeline.annotate(document);
		List<CoreMap> sentencesAnnotated = document.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentencesAnnotated) {
			Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
			sentiment = RNNCoreAnnotations.getPredictedClass(tree);
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				words.add(token.getString(CoreAnnotations.OriginalTextAnnotation.class));
				pos.add(token.get(CoreAnnotations.PartOfSpeechAnnotation.class));
				ner.add(token.getString(CoreAnnotations.CoarseNamedEntityTagAnnotation.class));
				lemma.add(token.getString(CoreAnnotations.LemmaAnnotation.class));
			}
		}
	}
	
	
	public static void main(String[] args) {
		String input = "I really love cheese.";
		CoreNLP coreNLP = new CoreNLP();
		coreNLP.annotate(input);
		ArrayList<String> words = coreNLP.words;;
		ArrayList<String> pos = coreNLP.pos;
		ArrayList<String> ne = coreNLP.ner;
		int i = coreNLP.sentiment;
		System.out.println(words);
		System.out.println(pos);
		System.out.println(ne);
		System.out.println(i);
	}
}
