package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.CardType;

public class gameSetupTests {
	
	
	
	@BeforeClass
	public static void setUp() {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.loadConfigFiles();
		
	}
	
	@Test
	public void testLoadedPlayers() {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.loadConfigFiles();
		
		// Test the three possible human players.
		assertTrue(board.getHumanPlayerNames().contains("Ms. Scarlett"));
		assertTrue(board.getHumanPlayerNames().contains("Mr. Green"));
		assertTrue(board.getHumanPlayerNames().contains("Col. Mustard"));
		
		// Test the four possible computer players.
		assertTrue(board.getComputerPlayerNames().contains("Prof. Plum"));
		assertTrue(board.getComputerPlayerNames().contains("Mrs. Peacock"));
		assertTrue(board.getComputerPlayerNames().contains("Mrs. White"));
		assertTrue(board.getComputerPlayerNames().contains("Dr. Mr. Prof. Scott Strong"));
		
	}
	
	@Test
	public void testDeckOfCards() {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.loadConfigFiles();
		
		// Should be of size 24 with 7 players, 6 weapons, and 9 rooms.
		assertEquals(24, board.getDeck().size());
		assertEquals(7, board.getNumPeople());
		assertEquals(6, board.getNumWeapons());
		assertEquals(11, board.getNumRooms());
		assertEquals(board.getCard("Greenhouse").getType(), CardType.ROOM);
		assertEquals(board.getCard("Dagger").getType(), CardType.WEAPON);
		assertEquals(board.getCard("Mr. Green").getType(), CardType.PERSON);
		
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