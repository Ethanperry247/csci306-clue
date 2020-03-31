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
	
	// all methods below to be implemented
	
	public BoardCell pickLocation(Set<BoardCell> targets, BoardCell justVisited) {	// parameters are set of calculated target cells and the doorway cell of most recently visited room
		ArrayList<BoardCell> rooms = new ArrayList<BoardCell>();	
		
		for (BoardCell cell : targets) {	// finds cells that are doorways (able to enter into those rooms)
			if (cell.isDoorway()) {
				rooms.add(cell);
			}
		}
		
		if (rooms.isEmpty() || (justVisited != null && rooms.contains(justVisited))) {	// if there is no rooms to go into or room just visited is an option...
			rooms = new ArrayList<BoardCell>(targets);
			Random rand = new Random();
			int n = rand.nextInt(rooms.size());					// ...select an available target randomly
			return rooms.get(n);
		} else {
			return rooms.get(0);								// else, room that was not just visited must be selected
		}
		
	}
	
	
	public void makeAccusation() {
		
	}
	
	// has TBD parameter
	public void createSuggestion() {
		
	}

}
