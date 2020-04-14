// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
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
		boolean justThere = false;
		
		for (BoardCell cell : targets) {	// finds cells that are doorways (able to enter into those rooms)
			if (cell.isDoorway()) {
				rooms.add(cell);
			}
			
			if (justVisited != null && cell.getInitial() == justVisited.getInitial()) {	// checks if room was just visited or not
				justThere = true;
			}
		}
		
		if (rooms.isEmpty() || justThere == true) {	// if there is no rooms to go into or room just visited is an option...
			rooms = new ArrayList<BoardCell>(targets);
			Random random = new Random();
			int someCell = random.nextInt(rooms.size());					// ...select an available target randomly
			return rooms.get(someCell);
		} else {
			return rooms.get(0);								// else, room that was not just visited must be selected
		}
		
	}
	
	@Override
	public void makeMove(Set<BoardCell> targets) {
		BoardCell target = pickLocation(targets); // Pick a location.
		setRow(target.getRow()); // Update row and column.
		setCol(target.getColumn());
	}
	
	
	public void makeAccusation() {
		
	}
	

	public Solution createSuggestion(Set<Card> peopleDeck, BoardCell location, Set<Card> weaponsDeck) {
		
		ArrayList<Card> missing = new ArrayList<Card>();	
		Random random = new Random();
		
		for (Card person : peopleDeck) {
			if (!playersSeen.contains(person)) {
				missing.add(person);
			}
		}
		
		int someCard = random.nextInt(missing.size());
		String person = missing.get(someCard).getName();
		missing.clear();
				
	
		for (Card weapon : weaponsDeck) {
			if (!weaponsSeen.contains(weapon)) {
				missing.add(weapon);
			}
		}
		
		someCard = random.nextInt(missing.size());
		String weapon = missing.get(someCard).getName();
		
		char initial = location.getInitial();
		String room = String.valueOf(initial);

		
		Solution accusation = new Solution(person, room, weapon);
		return accusation;
	}

}
