package test;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CoreNlpExample {
	public static void main(String[] args) {
	
	Properties props = new Properties();
	props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
	StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	
	String text = "Hello my name is Joel";
	
	Annotation document = new Annotation(text);
	pipeline.annotate(document);
	
	List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
	
	for(CoreMap sentence: sentences) {
		for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
			String word = token.getString(CoreAnnotations.OriginalTextAnnotation.class); // Text of the token
			String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class); // POS tag
			String ne = token.getString(CoreAnnotations.CoarseNamedEntityTagAnnotation.class); // Named Entity Label
		}
	}
	}

}
