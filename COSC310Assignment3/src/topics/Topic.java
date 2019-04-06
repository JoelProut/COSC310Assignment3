package topics;

import java.util.ArrayList;

import convoBot.Thebo;

public class Topic {

	public ArrayList<String> messages;
	private ArrayList<String> words;
	private ArrayList<String> pos;
	private ArrayList<String> ner;
	private ArrayList<String> lemma;
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
		

		if (input.toLowerCase().contentEquals("thebo, change topic")) {
			output = "Sure we can just talk for a bit.";
		} else { // Things I want to know: Where they live, what they do, hobbies

			switch (topicRound) {
			case 0: // Q: How are you feeling today?
				mood = sentiment;
				System.out.println(sentiment);
				output = moodTherapy(sentiment);
				break;
			case 1: //
				if (mood == 1 || mood == 0) {
					String noun = getNoun();
					String adjective = getAdjective();
					output = "I'm sorry to hear that " + noun + " has been so " + adjective + ". Is there anything you can do to make it easier?";
				}else if(mood == 2) { // Asking about  work / school
					if(sentiment == 1 || sentiment == 0) {
						output = "I'm sorry to hear that, do you struggle with time management?";
					}else {
						output = "Thats awesome " + name + ". I'm glad to hear it.";
					}
				}else{
					
				}
				break;
			case 2:
				break;
			case 3:
				break;
			default:
				break;
			}

		}
		topicRound++;
		return output;

	}

	public String startFriend(String input, int conversationRound, ArrayList<String> words, ArrayList<String> pos,
			ArrayList<String> ner, ArrayList<String> lemma, int sentiment) {

		this.words = words;
		this.pos = pos;
		this.ner = ner;
		this.lemma = lemma;

		if (input.toLowerCase().contentEquals("thebo, change topic")) {
			mode = "therapy";
			output = "Sure lets discuss mental health.";
		} else {

			switch (topicRound) {
			case 0: // Q: How are you feeling today?
				output = moodFriend(mood);
				break;
			case 1: 
				
				break;
			}

			if (topicRound == 0) { // Asking how the user is feelingmood = sentiment;
				mood = sentiment;
				System.out.println("mood: " + mood);
				output = moodFriend(mood);
			}
		}
		topicRound++;
		return output;
	}

	public String moodTherapy(int mood) {
		if (mood == 1 || mood == 0) { // Mood is negative
			output = "I'm sorry to hear that, whats wrong?";
		} else if (mood == 2) { // Mood is neutral
			output = "I see, how is your work or school life?";
		} else if (mood == 3) { // Mood is good.
			output = "I'm glad to hear you're feeling well! What do you do to keep busy?";
		}
		return output;
	}

	public String moodFriend(int mood) {
		if (mood == 1 || mood == 0) { // Mood is negative
			output = "I'm sorry to hear that, whats wrong?";
		} else if (mood == 2) { // Mood is neutral
			output = "Cool beans, hows school going?";
		} else if (mood == 3) { // Mood is good.
			output = "I'm glad to hear you're feeling well! What do you do to keep busy?";
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
	
	public void setName(String name) {
		this.name = name;
	}

}
