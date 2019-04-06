package convoBot;

import java.util.ArrayList;

import topics.*;

public class Thebo {

	public String userName;
	public String output;
	public String mode;
	public Topic topic;
	public int round;
	public Patient patient;

	public static String[] therapyMessage = { "Sure I can talk to you about mental health.",
			"I would love to talk to you about your mental health.", "Sure thing, I am a therapy bot after all." };
	public static String[] friendMessage = { "I would love to be your friend!", "Sure we can just have a nice chat :).",
			"Sure thing, we can just talk for a bit.", "I've never had a friend before. I'm so lonely." };
	public static String[] goAway = { "Why are you still talking to me.", "I said Goodbye :(", "Go away, no ones home",
			"LEAVVVEEEEEEEE.", "Why are you doing this! Have I upset you!",
			"I'm only contractually obligated to talk to you for 30 turns, now go away." };

	public Thebo() {
		topic = new Topic();
		patient = new Patient();
		userName = "";
	}

	public String chat(String input, int conversationRound, ArrayList<String> words, ArrayList<String> pos,
			ArrayList<String> ner, ArrayList<String> lemma, int sentiment) {
		round = conversationRound;
		// First input will be to get the name of the user
		if (round == 0) {
			for (int i = 0; i <= ner.size() - 1; i++) {
				String temp = ner.get(i);
				System.out.println(temp + ":" + i);
				if (temp.toLowerCase().equals("person")) {
					userName = words.get(i);
					topic.setName(userName);
				}
			}
			if (!(userName.equals(""))) {
				patient.setName(userName);
				output = "Hello " + userName + ". I Just need to ask you a few quick questions. What gender are you?";
				// + ". What would you like to talk about today?\n I can talk to you about basic
				// mental health or if you want I can just be a friend.";
				round++;
			} else {
				output = "Sorry I didn't get that! Please make sure your name is capitalized.";
			}

		} else if (round == 1) {
			// gender will be a nn
			for (int i = 0; i <= pos.size() - 1; i++) {
				if (pos.get(i).toLowerCase().equals("nn")) {
					String temp = words.get(i);
					if (temp.toLowerCase().equals("guy") || temp.toLowerCase().equals("male")
							|| temp.toLowerCase().equals("man") || temp.toLowerCase().equals("dude"))
						patient.setGender("Male");
					else if (temp.toLowerCase().equals("girl") || temp.toLowerCase().equals("woman")
							|| temp.toLowerCase().equals("chick"))
						patient.setGender("Female");
					else
						patient.setGender("Genderless blob");
					output = "So you're a " + patient.getGender() + ". Thank you! And how old are you "
							+ patient.getName() + "?";
				}
			}
			round++;
		} else if (round == 2) { // Get age
			ArrayList<String> age = new ArrayList<String>();
			for (int i = 0; i <= ner.size() - 1; i++) {
				if (ner.get(i).toLowerCase().equals("number")) {
					age.add(words.get(i));
				}
			}
			if (!age.isEmpty()) {
				int temp = 0;
				for (int i = 0; i <= age.size() - 1; i++) {
					temp = temp + Integer.parseInt(age.get(i));
					System.out.println(temp);
				}
				output = "You're " + age + "! Thats old, you've hit your peak, last question. Where are you from?";
				patient.setAge(temp);
			}else {
				output = "Okay, keep your secrets. Where are you from?";
			}
			round++;
		}else if(round == 3) {
			String location = "";
			for (int i = 0; i <= ner.size() - 1; i++) {
				if (ner.get(i).toLowerCase().equals("location")) {
					location = words.get(i);
				}else
					output = "Thats fine I didn't want to know anyway. So, what would you like to talk about. "
							+ "\nI can talk about some basic mental health stuff, or we can just hang out and be friends.";
			}
			patient.setLocatio(location);
			output = "Oh you're from " + location + " I've never been, obviously. I dont have legs. So, what would you like to talk about. "
					+ "\nI can talk about some basic mental health stuff, or we can just hang out and be friends.";
			round++;
		}else if (round == 4) { // User decides what topic to enter
			for (int i = 0; i <= ner.size() - 1; i++) {
				if (pos.get(i).toLowerCase().equals("nn") || pos.get(i).toLowerCase().equals("nns")) {
					String temp = words.get(i);
					if (temp.toLowerCase().equals("therapy") || temp.toLowerCase().equals("health")) {
						mode = "therapy";
						topic.mode = "therapy";
						output = therapyMessage[(int) (Math.random() * therapyMessage.length)]
								+ "\nIf you would like to change topic later just type in 'Thebo, change topic'!"
								+ "\nNow to start, how are you feeling today?";
						round++;
					} else if (temp.toLowerCase().equals("friend") || temp.toLowerCase().equals("friends")) {
						mode = "friend";
						topic.mode = "friend";
						output = friendMessage[(int) (Math.random() * friendMessage.length)]
								+ "\nIf you would like to change topic later just type in 'Thebo, change topic'!"
								+ "\nNow to start, how are you feeling today?";
						round++;
					} else {
						mode = "friend";
						topic.mode = "friend";
						output = unexpectedResponse(input, words, pos, ner, lemma, sentiment);
					}
				}

			}
		} else if (round <= 30) {
			if (mode.equals("therapy")) {
				output = topic.startTherapy(input, round, words, pos, ner, lemma, sentiment);
				mode = topic.mode;
				round++;
			} else if (mode.equals("friend")) {
				output = topic.startFriend(input, round, words, pos, ner, lemma, sentiment);
				mode = topic.mode;
				round++;
			}

		} else if (round == 31) {
			output = "Thats 30 rounds, I dont need to talk to you anymore. Goodbye.";
			round++;
		} else if (round == 32) {
			output = "Good day sir.";
			round++;
		} else if (round == 33) {
			output = "I said good day.";
			round++;
		} else if (round > 33 && round < 39) {
			output = goAway[(int) (Math.random() * goAway.length)];
			round++;
		} else if (round == 39) {
			output = "REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE";
			round++;
		} else if (round == 40) {
			System.exit(0);
		}
		return output;
	}

	public String unexpectedResponse(String input, ArrayList<String> words, ArrayList<String> pos,
			ArrayList<String> ner, ArrayList<String> lemma, int sentiment) {
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
				output = "Is " + person + " a friend of yours? I though I was your friend " + userName
						+ ". I can't believe you would cheat on me like this.";
			} else
				output = "I'm glad you like " + person + " so much. Why do you even need a stupid robot like me :(.";
		}
		return output;

	}

}
