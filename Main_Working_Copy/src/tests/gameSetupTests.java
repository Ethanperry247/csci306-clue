package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class gameSetupTests {
	
	@BeforeClass
	public static void setUp() {
		
	}
	
	@Test
	public void testLoadedPlayers() {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.loadConfigFiles();
		
		// Test the three possible human players.
		Player human = new HumanPlayer("Ms. Scarlett", 0, 3, "Red");
		assertTrue(board.getHumanPlayers().contains(human));
		human = new HumanPlayer("Mr. Green", 0, 9, "Green");
		assertTrue(board.getHumanPlayers().contains(human));
		human = new HumanPlayer("Col Mustard", 5, 19, "Yellow");
		assertTrue(board.getHumanPlayers().contains(human));
		
		// Test the four possible computer players.
		Player computer = new ComputerPlayer("Prof. Plum", 13, 19, "Purple");
		assertTrue(board.getComputerPlayers().contains(computer));
		computer = new ComputerPlayer("Mrs. Peacock", 19, 9, "Blue");
		assertTrue(board.getComputerPlayers().contains(computer));
		computer = new ComputerPlayer("Mrs. White", 12, 0, "White");
		assertTrue(board.getComputerPlayers().contains(computer));
		computer = new ComputerPlayer("Dr. Mr. Prof. Scott Strong", 19, 5, "Gray");
		assertTrue(board.getComputerPlayers().contains(computer));
		
	}
	
	@Test
	public void testDeckOfCards() {
		
	}
	
	@Test
	public void testGameActions() {
		
	}
	
	@Test
	public void testComputerTargetSelection() {
		
	}
	
	@Test
	public void testAccusation() {
		
	}
	
	@Test
	public void testComputerSuggestion() {
		
	}
	
	@Test
	public void testSuggestionDisproval() {
		
	}
	
	@Test
	public void testComputerSuggestionDisproval() {
		
	}
	
	@Test
	public void testSuggestionHandling() {
		
	}
	
}