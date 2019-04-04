package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.text.DefaultCaret;

public class chatBox extends JFrame {

	JPanel jPanel;
	JTextField textField;
	JTextArea textArea;
	JLabel jLabel;

	public chatBox() {
		buildChatBox();
	}

	public void buildChatBox() {
		setTitle("Thebo");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

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

		// Timer to change between thinking and typing
		Timer timer = new Timer(1, listener);
		timer.setInitialDelay(1000);

		// Key listener to detect if the user is typing or not
		KeyAdapter keyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent k) {
				jLabel.setText("Typing");
				timer.stop();
				if (k.getKeyCode() == KeyEvent.VK_ENTER) {
					printToChat(textField.getText());
					System.out.println(readInput());
				}
			}

			@Override
			public void keyReleased(KeyEvent k) {
				timer.start();
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
		textArea.append(s + "\n");
	}

	public void printToChat(String s) {
		if (s.trim().isEmpty()) {
			return;
		} else {
			textArea.append(s + "\n");
			textField.setText("");
			jLabel.setText("Messege Sent");
		}
	}

	public String readInput() {
		int i = textArea.getLineCount() - 1; // empty box counts as a line for some reason
		if (i >= 1) {
			String temp = textArea.getText();
			String[] lines = temp.split("\n");
			return lines[i-1] + " " + Integer.toString(i);
		} else {
			return "null";
		}

	}

	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatBox chat = new chatBox();
			}
		});
	}

}
