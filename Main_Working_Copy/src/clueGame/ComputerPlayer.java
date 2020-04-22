// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Random;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String playerName, int row, int column, String color) {
		super(playerName, row, column, color);
	}
	
	public ComputerPlayer(String playerName, int row, int column, String color, ArrayList<Card> weaponsSeen, ArrayList<Card> playersSeen) {
		super(playerName, row, column, color, weaponsSeen, playersSeen);
	}
	
	// all methods below to be implemented
	
	public BoardCell pickLocation(Set<BoardCell> targets) {	// parameters are set of calculated target cells and the doorway cell of most recently visited room
		ArrayList<BoardCell> rooms = new ArrayList<BoardCell>();
		ArrayList<BoardCell> available = new ArrayList<BoardCell>(targets);
		boolean justThere = false;
		
		for (BoardCell cell : targets) {	// finds cells that are doorways (able to enter into those rooms)
			if (cell.isDoorway()) {
				if (justVisited != null && cell.getInitial() == justVisited.getInitial()) {	// checks if room was just visited or not
					justThere = true;
				} else {
					rooms.add(cell);
				}
			}
		}
		
		if (rooms.isEmpty() || justThere == true) {	// if there is no rooms to go into or room just visited is an option...
			Random random = new Random();
			int someCell = random.nextInt(available.size());					// ...select an available target randomly
			return available.get(someCell);
		} else {
			justVisited = rooms.get(0);
			return rooms.get(0);								// else, room that was not just visited must be selected
		}
		
	}
	
	@Override
	public void makeMove() {
		Set<BoardCell> targets = getTargets();
		BoardCell target = pickLocation(targets); // Pick a location.
		setRow(target.getRow()); // Update row and column.
		setCol(target.getColumn());
	}
	
	
	// an accusation is made when the player's suggestion was not disproven in their last turn
	public Solution makeAccusation(Set<Card> weapons, Set<Card> rooms, Set<Card> players) {
		
		String weapon = ""; 
		String room = "";
		String player = "";
		
		for (Card card : weapons) {
			if (weaponsSeen.contains(card) == false) {
				weapon = card.getName();
			}
		}
		
		for (Card card : rooms) {
			if (roomsSeen.contains(card) == false) {
				room = card.getName();
			}
		}
		
		for (Card card : players) {
			if (playersSeen.contains(card) == false) {
				player = card.getName();
			}
		}
		
		Solution accusation = new Solution(player, room, weapon);
		return accusation;
	}
	
	public boolean readyAccusation(Set<Card> weapons, Set<Card> rooms, Set<Card> players) {
		
		if (weaponsSeen.size() != weapons.size()-1 || roomsSeen.size() != rooms.size()-1  || playersSeen.size() != players.size()-1) {
			return false;
		} else {
			return true;
		}
	}
	

	public Solution createSuggestion(Set<Card> peopleDeck, BoardCell location, Set<Card> weaponsDeck, Map<Character, ArrayList<String>> legend) {
		
		ArrayList<Card> missing = new ArrayList<Card>();	// an array list to keep track of cards not seen by player
		Random random = new Random();
		
		// iterates through player cards that have been seen, if card not seen add to missing cards array list
		for (Card person : peopleDeck) {
			if (!playersSeen.contains(person)) {
				missing.add(person);
			}
		}
		
		int someCard = random.nextInt(missing.size());		// picks a random player card among the missing cards
		String person = missing.get(someCard).getName();	// retrieves the random card's name value
		missing.clear();									// clears array list to populate with missing weapon cards next
				
		// iterates through weapon cards that have been seen, if card not seen add to missing cards array list
		for (Card weapon : weaponsDeck) {
			if (!weaponsSeen.contains(weapon)) {
				missing.add(weapon);
			}
		}
		
		someCard = random.nextInt(missing.size());			// picks a random weapon card among the missing cards
		String weapon = missing.get(someCard).getName();	// retrieves the random card's name value
		
		char initial = location.getInitial();				// gets initial of room (doorway) they are currently in
		String room = legend.get(initial).get(0);			// retrieves name of room from legend using initial

		Solution suggestion = new Solution(person, room, weapon);	// suggestion is created!
		return suggestion;
	}

}
