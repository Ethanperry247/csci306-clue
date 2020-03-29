// Authors: Caleb Pan, Ethan Perry

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.CardType;

public class gameSetupTests {
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {	// sets up the board and a variety of cards (of each type) 
		
		board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.loadConfigFiles();
		board.dealDeck();
		
	}
	
	@Test
	public void testLoadedPlayers() {
		
		// there are 7 loaded players, each player has 4 tests: if name, location (row and column), and color are all loaded correctly
		
		// Test the three possible human players.
		assertTrue(board.getHumanPlayerNames().contains("Ms. Scarlett"));	 // test if name has been loaded in correctly
		assertEquals(0, board.getPlayer("Ms. Scarlett").getRow());			 // tests if location has been loaded in correctly (row and column)
		assertEquals(3, board.getPlayer("Ms. Scarlett").getCol());
		assertEquals(Color.RED, board.getPlayer("Ms. Scarlett").getColor()); // tests if color has been loaded in correctly
		
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
		
		// tests for deck created: size deck of 24 with 7 players, 6 weapons, and 9 rooms
		
		assertEquals(24, board.getDeck().size());	// tests if expected amount of total cards are created 
		assertEquals(7, board.getNumPlayers());		// tests if expected amount of player cards are created 
		assertEquals(6, board.getNumWeapons());		// tests if expected amount of weapons cards are created 
		assertEquals(11, board.getNumRooms());		// tests if expected amount of room cards are created 
		
		// check if card types were loaded correctly
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