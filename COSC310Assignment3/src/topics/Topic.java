package topics;

import java.util.ArrayList;

import convoBot.Thebo;

public class Topic {

	public ArrayList<String> messages;
	private ArrayList<String> words;
	private ArrayList<String> pos;
	private ArrayList<String> ner;
	private ArrayList<String> lemma;
	private int sentiment;
	public String mode;
	public int topicRound;
	public String output;
	String name;
	public int mood;

	public Topic() {
		output = "";
		topicRound = 0;
		messages = new ArrayList<String>();

	}

	public String startTherapy(String input, int conversationRound, ArrayList<String> words, ArrayList<String> pos,
			ArrayList<String> ner, ArrayList<String> lemma, int sentiment) {

		this.words = words;
		this.pos = pos;
		this.ner = ner;
		this.lemma = lemma;
		this.sentiment = sentiment;
		this.topicRound = conversationRound;

		if (input.toLowerCase().contentEquals("thebo, change topic")) {
			output = "Sure we can just talk for a bit, ";
			topicRound = 1;
		} else { // Things I want to know: Where they live, what they do, hobbies

			switch (topicRound) {
			case 0: // Q: How are you feeling today?
				this.mood = sentiment;
				System.out.println(sentiment);
				output = moodTherapy(sentiment);
				topicRound++;
				break;
			case 1: //
				System.out.println("mood: " + mood);
				if (mood == 1 || mood == 0) {
					String noun = getNoun();
					String adjective = getAdjective();
					output = "I'm sorry to hear that " + noun + " has been so " + adjective
							+ ". Is there anything you can do to make it easier?";
				} else if (mood == 2) { // Asking about work / school
					if (sentiment == 1 || sentiment == 0) {
						output = "I'm sorry to hear that, do you struggle with time management?";
					} else {
						output = "That sounds fun";
					}
				} else {

				}
				topicRound++;
				break;
			case 2:
				topicRound++;
				break;
			case 3:
				topicRound++;
				break;
			default:
				topicRound++;
				break;
			}

		}
		return output;

	}

	public String startFriend(String input, int conversationRound, ArrayList<String> words, ArrayList<String> pos,
			ArrayList<String> ner, ArrayList<String> lemma, int sentiment) {

		this.words = words;
		this.pos = pos;
		this.ner = ner;
		this.lemma = lemma;
		this.sentiment = sentiment;

		if (input.toLowerCase().contentEquals("thebo, change topic")) {
			mode = "therapy";
			output = "Sure lets discuss mental health.";
			topicRound = 1;
		} else {

			switch (topicRound) {
			case 0: // Q: How are you feeling today?
				this.mood = sentiment;
				output = moodFriend(sentiment);
				topicRound++;
				break;
			case 1:
				if (mood == 1 || mood == 0) {
					String noun = getNoun();
					String adjective = getAdjective();
					output = "I'm sorry to hear that " + noun + " has been so " + adjective
							+ ". Is there anything you can do to make it easier?";
				} else if (mood == 2) { // Asking about work / school
					if (sentiment == 1 || sentiment == 0) {
						output = "I'm sorry to hear that, do you struggle with time management?";
					} else {
						output = "That sounds fun";
					}

				}
				topicRound++;
				break;
			default:
				output = unexpectedResponse();
				topicRound++;
				break;
			}
		}
		return output;
	}

	public String moodTherapy(int mood) {
		if (mood == 1 || mood == 0) { // Mood is negative
			output = "I'm sorry to hear that, whats wrong?";
		} else if (mood == 2) { // Mood is neutral
			output = "I see, how is your work or school life?";
		} else if (mood == 3 || mood == 4) { // Mood is good.
			output = "I'm glad to hear you're feeling well! What's got you in such a good mood?";
		}
		return output;
	}

	public String moodFriend(int mood) {
		if (mood == 1 || mood == 0) { // Mood is negative
			output = "I'm sorry to hear that best friend, whats wrong?";
		} else if (mood == 2) { // Mood is neutral
			output = "Cool beans, what do you do for fun?";
		} else if (mood == 3 || mood == 4) { // Mood is good.
			output = "I'm glad to hear you're feeling well! What do you do for fun?";
		}
		return output;
	}

	public String getNoun() {
		String noun = "";
		for (int i = 0; i <= pos.size() - 1; i++) {
			if (pos.get(i).toLowerCase().equals("nn") || pos.get(i).toLowerCase().equals("nnp")
					|| pos.get(i).toLowerCase().equals("nns") || pos.get(i).toLowerCase().equals("nnps"))
				noun = lemma.get(i);
		}
		return noun;
	}

	public String getAdjective() {
		String adjective = "";
		for (int i = 0; i <= pos.size() - 1; i++) {
			if (pos.get(i).toLowerCase().equals("jj") || pos.get(i).toLowerCase().equals("jjr")
					|| pos.get(i).toLowerCase().equals("jjs"))
				adjective = lemma.get(i);
		}
		return adjective;

	}
	
	public String unexpectedResponse() {
		String noun = "";
		for (int i = 0; i <= pos.size() - 1; i++) {
			if (pos.get(i).toLowerCase().equals("nn") || pos.get(i).toLowerCase().equals("nnp")
					|| pos.get(i).toLowerCase().equals("nns") || pos.get(i).toLowerCase().equals("nnps"))
				noun = lemma.get(i);
		}

		String verb = "";
		for (int i = 0; i <= pos.size() - 1; i++) {
			if (pos.get(i).toLowerCase().equals("vb") || pos.get(i).toLowerCase().equals("vbd")
					|| pos.get(i).toLowerCase().equals("vbp") || pos.get(i).toLowerCase().equals("vbz"))
				verb = lemma.get(i);
		}

		String adjective = "";
		for (int i = 0; i <= ner.size() - 1; i++) {
			if (pos.get(i).toLowerCase().equals("jj") || pos.get(i).toLowerCase().equals("jjr")
					|| pos.get(i).toLowerCase().equals("jjs"))
				adjective = lemma.get(i);
		}

		String person = "";
		for (int i = 0; i <= ner.size() - 1; i++) {
			if (ner.get(i).toLowerCase().equals("person"))
				person = words.get(i);
		}

		String location = "";
		for (int i = 0; i <= ner.size() - 1; i++) {
			if (ner.get(i).toLowerCase().equals("location"))
				person = words.get(i);
		}
		if (!noun.equals("")) {
			if (!adjective.equals("")) {
				if (sentiment == 0 || sentiment == 1)
					output = "I'm sorry to hear that " + noun + " has been so " + adjective
							+ ". Is there anything you can do to make it easier?";
				else if (sentiment == 2) {
					output = noun + " sounds interesting, do you like it?";
				} else
					output = "I'm glad you feel so good about " + noun
							+ ". It sounds like you're really passionate about it.";
			} else if (!verb.equals("")) {
				if (sentiment == 0 || sentiment == 1)
					output = "I'm sorry to hear that you " + verb + " " + noun
							+ ". I hope you dont feel that way forever.";
				else if (sentiment == 2) {
					output = noun + " sounds interesting, do you like it?";
				} else
					output = "I'm glad you " + verb + " " + noun
							+ ". It sounds like you're really passionate about it.";
			}
		} else if (!person.equals("")) {
			if (sentiment == 0 || sentiment == 1)
				output = "I'm sorry to hear that you feel that way about " + person + ".";
			else if (sentiment == 2) {
				output = "Is " + person + " a friend of yours? I though I was your friend " + name
						+ ". I can't believe you would cheat on me like this.";
			} else
				output = "I'm glad you like " + person + " so much. Why do you even need a stupid robot like me :(.";
		}
		return output;

	}

	public void setName(String name) {
		this.name = name;
	}

}
