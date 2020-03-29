package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

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
		assertEquals(0, board.getPlayer("Ms. Scarlett").getRow());
		assertEquals(3, board.getPlayer("Ms. Scarlett").getCol());
		assertEquals(Color.RED, board.getPlayer("Ms. Scarlett").getColor());
		assertTrue(board.getHumanPlayerNames().contains("Mr. Green"));
		assertEquals(0, board.getPlayer("Mr. Green").getRow());
		assertEquals(9, board.getPlayer("Mr. Green").getCol());
		assertEquals(Color.GREEN, board.getPlayer("Mr. Green").getColor());
		assertTrue(board.getHumanPlayerNames().contains("Col. Mustard"));
		assertEquals(5, board.getPlayer("Col. Mustard").getRow());
		assertEquals(19, board.getPlayer("Col. Mustard").getCol());
		assertEquals(Color.YELLOW, board.getPlayer("Col. Mustard").getColor());
		
		// Test the four possible computer players.
		assertTrue(board.getComputerPlayerNames().contains("Prof. Plum"));
		assertEquals(13, board.getPlayer("Prof. Plum").getRow());
		assertEquals(19, board.getPlayer("Prof. Plum").getCol());
		assertEquals(Color.MAGENTA, board.getPlayer("Prof. Plum").getColor());
		assertTrue(board.getComputerPlayerNames().contains("Mrs. Peacock"));
		assertEquals(19, board.getPlayer("Mrs. Peacock").getRow());
		assertEquals(9, board.getPlayer("Mrs. Peacock").getCol());
		assertEquals(Color.BLUE, board.getPlayer("Mrs. Peacock").getColor());
		assertTrue(board.getComputerPlayerNames().contains("Mrs. White"));
		assertEquals(12, board.getPlayer("Mrs. White").getRow());
		assertEquals(0, board.getPlayer("Mrs. White").getCol());
		assertEquals(Color.WHITE, board.getPlayer("Mrs. White").getColor());
		assertTrue(board.getComputerPlayerNames().contains("Dr. Mr. Prof. Scott Strong"));
		assertEquals(19, board.getPlayer("Dr. Mr. Prof. Scott Strong").getRow());
		assertEquals(5, board.getPlayer("Dr. Mr. Prof. Scott Strong").getCol());
		assertEquals(Color.GRAY, board.getPlayer("Dr. Mr. Prof. Scott Strong").getColor());
		
	}
	
	@Test
	public void testDeckOfCards() {
		
		Board board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.loadConfigFiles();
		board.dealDeck();
		
		// Should be of size 24 with 7 players, 6 weapons, and 9 rooms.
		assertEquals(24, board.getDeck().size());
		assertEquals(7, board.getNumPlayers());
		assertEquals(6, board.getNumWeapons());
		assertEquals(11, board.getNumRooms());
		assertEquals(board.getCard("Greenhouse").getType(), CardType.ROOM);
		assertEquals(board.getCard("Dagger").getType(), CardType.WEAPON);
		assertEquals(board.getCard("Mr. Green").getType(), CardType.PERSON);
		
		// since we have 7 players and 24 cards, 3 players should end up with 4 cards and 4 players end up with 3 cards
		assertTrue(board.getNumCardsDealt().contains(4));
		assertTrue(board.getNumCardsDealt().contains(3));
		assertTrue(board.getNumCardsDealt().size() == 2);	// verifies players either have 3 or 4 cards 
		
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