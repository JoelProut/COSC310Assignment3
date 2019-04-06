package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.event.*;
import javax.swing.text.DefaultCaret;

import convoBot.*;
import languageProcessing.*;
import topics.*;

public class chatBox extends JFrame {
	ArrayList<String> words;
	ArrayList<String> pos;
	ArrayList<String> ner;
	ArrayList<String> lemma;
	int sentiment;
	int conversationRound;

	JPanel jPanel;
	public static JTextField textField;
	JTextArea textArea;
	JLabel jLabel;
	
	public String input;
	public int i;
	public CoreNLP coreNLP;
	public Thebo thebo;
	
	public chatBox() {
		buildChatBox();
		botToChat("Hello, my name is Thebo! What's your name?");
	}

	public void buildChatBox() {
		coreNLP = new CoreNLP();
		thebo = new Thebo();
		setTitle("Thebo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		conversationRound = 0;
		i = 0;

		// Create JPanel
		jPanel = new JPanel();
		jPanel.setLayout(new GridLayout(2, 1));
		jLabel = new JLabel();
		jPanel.add(jLabel);

		// Make an action listener
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jLabel.setText("");
			}
		};

		// Timer to change between idle and typing
		Timer timer = new Timer(1, listener);
		timer.setInitialDelay(1000);

		// Key listener to detect if the user is typing or not
		KeyAdapter keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent k) {
				jLabel.setText("Typing");
				timer.stop();
				if (k.getKeyCode() == KeyEvent.VK_ENTER) {
					input = textField.getText();
					printToChat(textField.getText());
					//System.out.println(readInput());
				}
			}

			@Override
			public void keyReleased(KeyEvent k) {
				timer.start();
				if(k.getKeyCode() == KeyEvent.VK_ENTER) {
					// First get the NLP analysis
					coreNLP.annotate(input);
					words = coreNLP.words;
					pos = coreNLP.pos;
					ner = coreNLP.ner;
					lemma = coreNLP.lemma;
					sentiment = coreNLP.sentiment;
					// Chat bot logic
					botToChat(thebo.chat(input, conversationRound, words, pos,ner,lemma, sentiment));
					conversationRound = thebo.round;
					// For trouble shooting
					System.out.println(words);
					System.out.println(pos);
					System.out.println(ner);
					System.out.println(lemma);
					System.out.println(sentiment);
					System.out.println(conversationRound);
				}
			}
		};

		DefaultCaret caret = new DefaultCaret() {
			@Override
			public boolean isSelectionVisible() {
				return true;
			}
		};

		// ChatBox
		// Text Field
		textField = new JTextField();
		textField.addKeyListener(keyListener);
		jPanel.add(textField);
		add(jPanel, BorderLayout.SOUTH);

		// Text Area
		textArea = new JTextArea();
		textArea.setCaret(caret);
		textArea.setMargin(new Insets(5, 5, 5, 5));
		textArea.setEditable(false);
		jPanel.add(textField);
		add(jPanel, BorderLayout.SOUTH);
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane);

		addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent we) {
				// Get the focus when window is opened
				textField.requestFocus();
			}
		});
		setSize(500, 500);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void botToChat(String s) {
		textArea.append("Thebo: " + s + "\n\n");
	}

	public void printToChat(String s) {
		if (s.trim().isEmpty()) {
			return;
		} else {
			textArea.append("User: " + s + "\n\n");
			textField.setText("");
			jLabel.setText("Messege Sent");
		}
	}

//	public String readInput() {
//		int i = textArea.getLineCount() - 1; // empty box counts as a line for some reason
//		String input = "";
//		if (i >= 1) {
//			String temp = textArea.getText();
//			String[] lines = temp.split("\n");
//			String line =  lines[i-1];
//			String[] temp2 = line.split(" ");
//			for(int j = 1; j < temp2.length; j++) {
//				if (j == 1)
//						input = temp2[j];
//				else
//					input = input + " " + temp2[j];
//			}
//			return input;
//		} else {
//			return "null";
//		}
//
//	}


	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatBox chat = new chatBox();
			}
		});
	}

}
