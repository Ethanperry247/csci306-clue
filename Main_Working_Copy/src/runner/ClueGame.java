package runner;

import GUI.*;
import clueGame.*;

public class ClueGame {
	private MainGUI mainGUI;
	private Board board;

	public ClueGame() {
		// Create the game board.
		board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.loadConfigFiles();
		board.createSolution();
		board.dealDeck();
		board.playTurn(); // Play the first round.
		
		// Create the GUI.
		mainGUI = new MainGUI("CLUE");
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}

}
