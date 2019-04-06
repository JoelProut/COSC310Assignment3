package convoBot;

import java.util.ArrayList;

import topics.*;

public class Thebo {
	
	public String userName;
	public String output;
	public String mode;
	public Topic topic;
	public int round;
	
	public static String[] therapyMessage = { "Sure I can talk to you about mental health.", "I would love to talk to you about your mental health.",
	"Sure thing, I am a therapy bot after all." };
	public static String[] friendMessage = { "I would love to be your friend!", "Sure we can just have a nice chat :).",
	"Sure thing, we can just talk for a bit.", "I've never had a friend before. I'm so lonely."};
	public static String[] goAway = { "Why are you still talking to me.", "I said Goodbye :(",
			"Go away, no ones home", "LEAVVVEEEEEEEE.", "Why are you doing this! Have I upset you!", "I'm only contractually obligated to talk to you for 30 turns, now go away."};
	
	public Thebo() {
		topic = new Topic();
		userName = "";
	}
	
	public String chat(String input, int conversationRound,  ArrayList<String> words, ArrayList<String> pos, ArrayList<String> ner, ArrayList<String> lemma, int sentiment) {
		round = conversationRound;
		// First input will be to get the name of the user
		if(round == 0) {
			for (int i = 0; i <= ner.size() - 1; i++) {
				String temp = ner.get(i);
				System.out.println(temp + ":" + i);
				if(temp.toLowerCase().equals("person")) {
					userName = words.get(i);
					topic.setName(userName);

				}
			}
			if(!(userName.equals(""))) {
				output = "Hello " + userName + ". What would you like to talk about today?\n I can talk to you about basic mental health or if you want I can just be a friend.";
				round++;
			}else {
				output = "Sorry I didn't get that! Please make sure your name is capitalized.";
			}
			
		}else if(round == 1){ // User decides what topic to enter
			for (int i = 0; i<= ner.size() - 1; i++) {
				if(pos.get(i).toLowerCase().equals("nn") || pos.get(i).toLowerCase().equals("nns")) {
					String temp = words.get(i);
					if(temp.toLowerCase().equals("therapy") || temp.toLowerCase().equals("health")){
						mode = "therapy";
						topic.mode = "therapy";
						output = therapyMessage[(int) (Math.random() * therapyMessage.length)] + "\nIf you would like to change topic later just type in 'Thebo, change topic'!"
								+ "\nNow to start, how are you feeling today?";
						round++;
					}else if(temp.toLowerCase().equals("friend") || temp.toLowerCase().equals("friends")) {
						mode = "friend";
						topic.mode = "friend";
						output = friendMessage[(int) (Math.random() * friendMessage.length)] + "\nIf you would like to change topic later just type in 'Thebo, change topic'!"
								+ "\nNow to start, how are you feeling today?";
						round++;
					}
				}
				
			}
		}else if(round <= 30) {
			if(mode.equals("therapy")) {
				output = topic.startTherapy(input, round, words, pos, ner, lemma, sentiment);
				mode = topic.mode;
				round++;
			}else if(mode.equals("friend")) {
				output = topic.startFriend(input, round, words, pos, ner,lemma, sentiment);
				mode = topic.mode;
				round++;
			}
			
		}else if(round == 31){
			output = "Thats 30 rounds, I dont need to talk to you anymore. Goodbye.";
			round++;
		}else if(round == 32) {
			output = "Good day sir.";
			round++;
		}else if(round == 33) {
			output = "I said good day.";
			round++;
		}else if(round > 33 && round < 39) {
			output = goAway[(int) (Math.random() * goAway.length)];
			round++;
		}else if(round == 39) {
			output = "REEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE";
			round++;
		}
		else if(round == 40) {
			System.exit(0);
		}
		return output;
	}

}
