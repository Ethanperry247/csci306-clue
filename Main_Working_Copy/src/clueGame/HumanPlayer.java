// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {
	private boolean playerMoved = false;

	public HumanPlayer(String playerName, int row, int column, String color) {
		super(playerName, row, column, color);
	}
	
	@Override
	public void makeMove(Set<BoardCell> targets) {
		playerMoved = false;
		
	}
	
	public boolean hasPlayerMoved() {
		return playerMoved;
	}
	
	public void move(int row, int col) {
		setRow(row);
		setCol(col);
		playerMoved = true;
	}

}
