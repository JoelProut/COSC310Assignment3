package test;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.SwingUtilities;

import gui.*;
import languageProcessing.*;

public class testChatBot {

	public static void main(String[] args) {
		String input;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatBox chat = new chatBox();
				chat.botToChat("Hello");
			}
		});

	}

}
