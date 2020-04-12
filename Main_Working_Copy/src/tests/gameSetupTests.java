// Authors: Caleb Pan, Ethan Perry

package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

@TestMethodOrder(OrderAnnotation.class)	// executes tests in order since testSuggestionHandling will need to go last
public class gameSetupTests {
	
	private static Board board;
	
	@BeforeAll
	public static void setUp() {	// sets up the board and a variety of cards (of each type) 
		
		board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		board.initialize();
		board.loadConfigFiles();
		board.createSolution("Mr. Green", "Dagger", "Greenhouse");
		board.dealDeck();
		
	}
	
	@Test
	@Order(1) 
	public void testLoadedPlayers() {
		
		// there are 7 loaded players, each player has 4 tests: if name, location (row and column), and color are all loaded correctly
		
		// Test the one possible human players.
		assertTrue(board.getHumanPlayerNames().contains("Ms. Scarlett"));	 // test if name has been loaded in correctly
		assertEquals(0, board.getPlayer("Ms. Scarlett").getRow());			 // tests if location has been loaded in correctly (row and column)
		assertEquals(3, board.getPlayer("Ms. Scarlett").getCol());
		assertEquals(Color.RED, board.getPlayer("Ms. Scarlett").getColor()); // tests if color has been loaded in correctly
		
		// Test remaining computer players.
		assertTrue(board.getComputerPlayerNames().contains("Mr. Green"));
		assertEquals(0, board.getPlayer("Mr. Green").getRow());
		assertEquals(9, board.getPlayer("Mr. Green").getCol());
		assertEquals(Color.GREEN, board.getPlayer("Mr. Green").getColor());
		
		assertTrue(board.getComputerPlayerNames().contains("Col. Mustard"));
		assertEquals(5, board.getPlayer("Col. Mustard").getRow());
		assertEquals(19, board.getPlayer("Col. Mustard").getCol());
		assertEquals(Color.YELLOW, board.getPlayer("Col. Mustard").getColor());
		
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
	@Order(2) 
	public void testDeckOfCards() {
		
		// tests for deck created: size deck of 22 with 7 players, 6 weapons, and 9 rooms
		// However, 3 cards (the solution cards) should not be found in the deck.
		// So the total number of cards should be 19.
		
		assertEquals(19, board.getDeck().size());	// tests if expected amount of total cards are created 
		assertEquals(6, board.getNumPlayers());		// tests if expected amount of player cards are created 
		assertEquals(5, board.getNumWeapons());		// tests if expected amount of weapons cards are created 
		assertEquals(8, board.getNumRooms());		// tests if expected amount of room cards are created 
		
		// check if card types were loaded correctly
		assertEquals(board.getCard("Dungeon").getType(), CardType.ROOM);	
		assertEquals(board.getCard("Rope").getType(), CardType.WEAPON);
		assertEquals(board.getCard("Ms. Scarlett").getType(), CardType.PERSON);
		
		// since we have 7 players and 21 cards, all players should end up with 3 cards.
		assertTrue(board.getNumCardsDealt().contains(3));
		assertTrue(board.getNumCardsDealt().size() == 2);	// verifies players either have 3 or 4 cards
		
	}
	
	@Test
	@Order(3) 
	public void testGameActions() {
		
	}
	
	
	@Test
	@Order(4) 
	public void testAccusation() {
		
		// Tests the correct solution, one incorrect person, incorrect room, and incorrect weapon.
		Solution correctSolution = new Solution("Mr. Green", "Dagger", "Greenhouse");
		Solution incorrectPlayer = new Solution("Ms. Scarlett", "Dagger", "Greenhouse");
		Solution incorrectWeapon = new Solution("Mr. Green", "Rope", "Greenhouse");
		Solution incorrectRoom = new Solution("Mr. Green", "Dagger", "Dungeon");
		
		
		assertTrue(board.checkAccusation(correctSolution));
		assertFalse(board.checkAccusation(incorrectPlayer));
		assertFalse(board.checkAccusation(incorrectWeapon));
		assertFalse(board.checkAccusation(incorrectRoom));
	}
	
	@Test
	@Order(5) 
	public void testComputerSuggestion() {
		
		// in this scenario, a computer player is in the Veranda (V) and they have not seen any cards
		ComputerPlayer playerOne = new ComputerPlayer("GLaDOS", 0, 0, "RED"); 
		BoardCell location = board.getCellAt(playerOne.getRow(),playerOne.getRow());
		Solution testOne = playerOne.createSuggestion(board.getPlayers(), location, board.getWeapons());
		
		// therefore, the Room in their suggestion would be where they currently are: V 
		assertEquals(testOne.getRoom(), "V");
		// since they have not seen any cards, they will pick a random Person and Weapon for their suggestion
		assertTrue(board.getAllPlayerNames().contains(testOne.getPerson()));
		assertTrue(board.getWeaponNames().contains(testOne.getWeapon()));
	
		// set up so that in our next scenario the computer player has not seen only one weapon and one person
		ArrayList<Card> seenWeapons = new ArrayList<Card>(board.getWeapons());
		ArrayList<Card> seenPeople = new ArrayList<Card>(board.getPlayers());
		Card missingWeapon = new Card("Rope", CardType.WEAPON);
		Card missingPerson = new Card("Mrs. White", CardType.PERSON);
		seenWeapons.remove(missingWeapon);								// missing weapon control case: only Rope has not been seen
		seenPeople.remove(missingPerson);								// missing person control case: only Mrs. White has not been seen
		
		// in this scenario, a computer player is in the Dungeon (D) and they have seen all weapon and people cards except our control cases
		ComputerPlayer playerTwo = new ComputerPlayer("HAL9000", 19, 19, "RED", seenWeapons, seenPeople); 
		location = board.getCellAt(playerTwo.getRow(),playerTwo.getRow());
		Solution testTwo = playerTwo.createSuggestion(board.getPlayers(), location, board.getWeapons());
		
		assertTrue(testTwo.getPerson().equals("Mrs. White"));	// will suggest the missing person control case
		assertTrue(testTwo.getWeapon().equals("Rope"));			// will suggest the missing weapon control case
		assertEquals(testTwo.getRoom(), "D");					// will suggest current room: D
		
	}
	
	@Test
	@Order(6) 
	public void testComputerTargetSelection() {
		
		// our computer player to get select targets
		ComputerPlayer playerOne = new ComputerPlayer("Ultron", 0, 0, "RED");
		
		// there is an unvisited room, picks to go to the room
		board.calcTargets(19, 10, 1);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(playerOne.pickLocation(targets), board.getCellAt(19, 11));
		
		// just visited the nearby available room, so will pick randomly
		playerOne.updateJustVisited(board.getCellAt(19,14));
		assertTrue(targets.contains(playerOne.pickLocation(targets)));
		
		// no rooms to visit, picks random
		board.calcTargets(13, 19, 4);
		targets = board.getTargets();
		assertTrue(targets.contains(playerOne.pickLocation(targets)));
		
	}
	
	@Test
	@Order(7) 
	public void testSuggestionDisproval() {
		
		Player player = new HumanPlayer("Mr. Caleb Pan", 0, 0, "RED"); // Create a dummy player.
		Card weapon = new Card("Weapon", CardType.WEAPON);
		Card room = new Card("Room", CardType.ROOM);
		Card person = new Card("Person", CardType.PERSON);
		
		player.addCard(weapon); // Give the player three arbitrary cards.
		player.addCard(room);
		player.addCard(person);
		
		// Create a suggestion without any matching cards to the player's hand.
		Solution suggestion = new Solution("NotMatching", "NotMatching", "NotMatching");
		assertEquals(null, player.disproveSuggestion(suggestion));
		
		// Create a suggestion with one matching card in the player's hand.
		suggestion = new Solution("Weapon", "NotMatching", "NotMatching");
		assertEquals(weapon, player.disproveSuggestion(suggestion));

		// Create a suggestion with three matching card in the player's hand.
		suggestion = new Solution("Weapon", "Room", "Person");
		
		// Create a list of all matching cards since disprove suggestion will return them randomly.
		ArrayList<Card> matchingCards = new ArrayList<Card>();
		matchingCards.add(weapon);
		matchingCards.add(room);
		matchingCards.add(person);
		// Player returns s random disproving card, which should be contained in the matching cards list.
		assertTrue(matchingCards.contains(player.disproveSuggestion(suggestion)));
		
		// To test this further, we use three booleans to check and affirm that all cards in hand are all return at least once.
		boolean weaponReturned = false;
		boolean roomReturned = false;
		boolean personReturned = false;
		
		// We do this, as mentioned in the design document, by looping so that each matching card is returned.
		// First for the weapon.
		for (int i = 0; i < 100; i++) {
			if (weapon.equals(player.disproveSuggestion(suggestion))) {
				weaponReturned = true;
			}
		}
		// Demonstrates that a weapon was returned by the disprove suggestion method.
		assertTrue(weaponReturned);
		
		for (int i = 0; i < 100; i++) {
			if (room.equals(player.disproveSuggestion(suggestion))) {
				roomReturned = true;
			}
		}
		// Demonstrates that a room was returned by the disprove suggestion method.
		assertTrue(roomReturned);
		
		for (int i = 0; i < 100; i++) {
			if (person.equals(player.disproveSuggestion(suggestion))) {
				personReturned = true;
			}
		}
		// Demonstrates that a person was returned by the disprove suggestion method.
		assertTrue(personReturned);
		
	}
	
	@Test
	@Order(8)
	public void testComputerSuggestionDisproval() {
		
	}
	

	@Test
	@Order(9) 
	public void testSuggestionHandling() {
		board.removePlayers(); // Clears the board so that we only have to deal with the players we need.
		// Create a fake board and set its configuration files.
		Board fakeBoard = Board.getInstance();
		fakeBoard.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");
		
		Player playerOne = new HumanPlayer("Mr. Caleb Pan", 0, 0, "RED"); // Create a dummy player.
		Card weapon = new Card("Candlestick", CardType.WEAPON);
		Card room = new Card("Lounge", CardType.ROOM);
		Card person = new Card("Mr. Green", CardType.PERSON);
		
		playerOne.addCard(weapon); // Give the dummy player three arbitrary cards.
		playerOne.addCard(room);
		playerOne.addCard(person);
		
		fakeBoard.addPlayer(playerOne); // Add that fake player to the board.
		
		Player playerTwo = new ComputerPlayer("Mr. Another Player", 0, 0, "RED"); // Create a dummy player.
		weapon = new Card("Rope", CardType.WEAPON);
		room = new Card("Dungeon", CardType.ROOM);
		person = new Card("Mrs. White", CardType.PERSON);
		
		playerTwo.addCard(weapon); // Give the dummy player three arbitrary cards.
		playerTwo.addCard(room);
		playerTwo.addCard(person);
		
		fakeBoard.addPlayer(playerTwo); // Add that fake player to the board.
		
		Player playerThree = new ComputerPlayer("Mr. New Player", 0, 0, "RED"); // Create a dummy player.
		weapon = new Card("Dagger", CardType.WEAPON);
		room = new Card("Greenhouse", CardType.ROOM);
		person = new Card("Prof. Plum", CardType.PERSON);
		
		playerThree.addCard(weapon); // Give the dummy player three arbitrary cards.
		playerThree.addCard(room);
		playerThree.addCard(person);
		
		fakeBoard.addPlayer(playerThree); // Add that fake player to the board.
		
		
		// We now have a fake board populated with fake players (each with three cards).
		Solution suggestion = new Solution("NoMatches", "NoMatches", "NoMatches"); // Suggestion with no possible matching cards in the hands of the players.
		assertEquals(null, fakeBoard.handleSuggestion(playerOne, suggestion)); // Affirm that the board handles the suggestion and returns null.
		
		// The following queries test playerOne, the human player.
		// This below query also satisfies the human being the accuser and suggesting his own card.
		suggestion = new Solution("Candlestick", "NoMatches", "NoMatches"); // Suggestion with only a matching card in the hands of the accusing player.
		assertEquals(null, fakeBoard.handleSuggestion(playerOne, suggestion)); // Affirm that the board handles the suggestion and returns null.
		
		suggestion = new Solution("Rope", "NoMatches", "NoMatches"); // Suggestion with a matching card in the hands of the second player.
		assertEquals(new Card("Rope", CardType.WEAPON), fakeBoard.handleSuggestion(playerOne, suggestion)); // Affirm that the board handles the suggestion and returns the correct card.
	
		suggestion = new Solution("Dagger", "NoMatches", "NoMatches"); // Suggestion with a matching card in the hands of the third player.
		assertEquals(new Card("Dagger", CardType.WEAPON), fakeBoard.handleSuggestion(playerOne, suggestion)); // Affirm that the board handles the suggestion and returns the correct card.
	
		suggestion = new Solution("Rope", "Greenhouse", "NoMatches"); // Suggestion with a matching card in the hands of the second and third player.
		assertEquals(new Card("Rope", CardType.WEAPON), fakeBoard.handleSuggestion(playerOne, suggestion)); // Affirm that the board handles the suggestion and returns the card of player two, not three.
	
		// The following queries test playerTwo and playerThree, the computer players.
		suggestion = new Solution("CandleStick", "Greenhouse", "NoMatches"); // Suggestion with a matching card in the hands of playerOne and playerThree. 
		assertEquals(new Card("Greenhouse", CardType.ROOM), fakeBoard.handleSuggestion(playerTwo, suggestion)); // Affirm that the board handles the suggestion and returns the card of playerThree.
	
		suggestion = new Solution("Candlestick", "NoMatches", "NoMatches"); // Suggestion with a matching card in the hands of playerOne and playerThree. 
		assertEquals(new Card("Candlestick", CardType.WEAPON), fakeBoard.handleSuggestion(playerTwo, suggestion)); // Affirm that the board handles the suggestion and returns the card of the human player.
	
	}
	
}

