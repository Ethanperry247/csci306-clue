// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {
	private boolean playerMoved = false;
	private boolean accusationMade = false;

	public HumanPlayer(String playerName, int row, int column, String color) {
		super(playerName, row, column, color);
	}
	
	@Override
	public void makeMove() {
		playerMoved = false;
	}
	
	public boolean getPlayerMovementStatus() {
		return playerMoved;
	}
	
	public void move(int row, int col) {
		setRow(row);
		setCol(col);
		playerMoved = true;
	}
	
	public void setAccusationMade(boolean accusationMade) {
		this.accusationMade = accusationMade;
	}
	
	public boolean getAccusationMade() {
		return accusationMade;
	}

}
