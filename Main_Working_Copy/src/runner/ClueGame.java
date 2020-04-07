package runner;

import GUI.*;
import clueGame.*;

public class ClueGame {
	MainGUI mainGUI;
	Board board;

	public ClueGame() {
		board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.loadConfigFiles();
		board.createSolution("Mr. Green", "Dagger", "Greenhouse");
		board.dealDeck();
		mainGUI = new MainGUI("CLUE");
	}

	public static void main(String[] args) {
		ClueGame game = new ClueGame();
	}

}
