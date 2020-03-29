// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player{

	public ComputerPlayer(String playerName, int row, int column, String color) {
		super(playerName, row, column, color);
	}
	
	// all methods below to be implemented
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell x = new BoardCell(0,0,"W");
		return x;
	}
	
	public void makeAccusation() {
		
	}
	
	// has TBD parameter
	public void createSuggestion() {
		
	}

}
