package languageProcessing;

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
import java.io.PrintWriter;

public class CoreNLP {
	// The CoreNLP class will take in the user input and return: Named entity
	// recognition and POS tagging depending on the method.
	public Properties props;
	public ArrayList<String> words = new ArrayList<String>();
	public ArrayList<String> partOfSpeach = new ArrayList<String>();
	public ArrayList<String> namedEntity = new ArrayList<String>();

	public CoreNLP(String input) {
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(input);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				words.add(token.getString(CoreAnnotations.OriginalTextAnnotation.class));
				partOfSpeach.add(token.get(CoreAnnotations.PartOfSpeechAnnotation.class));
				namedEntity.add(token.getString(CoreAnnotations.CoarseNamedEntityTagAnnotation.class));
			}
		}

	}
	
	public static void main(String[] args) {
		CoreNLP coreNLP = new CoreNLP("I love Ryley Jane");
		ArrayList<String> words = coreNLP.words;
		ArrayList<String> pos = coreNLP.partOfSpeach;
		ArrayList<String> ne = coreNLP.namedEntity;
		System.out.println(words);
		System.out.println(pos);
		System.out.println(ne);
	}
}
