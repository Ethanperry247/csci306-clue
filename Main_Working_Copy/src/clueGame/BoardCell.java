// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	
	private int row;
	private int column;
	private String initial;
	public static final int DOOR_DEPTH = Board.CELL_LENGTH/5;
	

	public BoardCell(int row, int column, String initial) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		Board.getInstance();
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
	public boolean isDoorway() {
		if (this.initial.length() == 1) {	// returns false if cell is only of length one or is not of door format.
			return false;
		} else if (initial.charAt(1) == 'R' ||
					initial.charAt(1) == 'L' ||
					initial.charAt(1) == 'U' ||
					initial.charAt(1) == 'D'){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isWalkway() {
		if (this.initial.equals("W")) {	// checks initial indicating cell is a walkway
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isRoom() {
		// if statement to confirm the cell is not a walkway, closet, or door
		if (!this.initial.equals("W") && !this.initial.equals("X") && this.initial.length() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public DoorDirection getDoorDirection() {
		
		if (this.initial.length() == 1) {	// returns none if cell has no door
			return DoorDirection.NONE;
		} else {	// else, a switch case to determine which direction for the door
			switch(this.initial.charAt(1)) {
				case 'L': 
					return DoorDirection.LEFT;
				case 'R':
					return DoorDirection.RIGHT;
				case 'U':
					return DoorDirection.UP;
				case 'D':
					return DoorDirection.DOWN;
				default:
					return null;
			}
		}	
	}
	
	public char getInitial() {
		// Returns a character rather than a string because that is what
		// Dr. Baldwin's test code requires.
		return initial.charAt(0);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	// draws cells of board for BoardGUI
	public void draw(Graphics cell) {
		
		if (this.initial.equals("W")) {		// if cell is a walkway, color in yellow and border with black line
			cell.setColor(Color.YELLOW);
			cell.fillRect(column*Board.CELL_LENGTH, row*Board.CELL_LENGTH, Board.CELL_LENGTH, Board.CELL_LENGTH);
			cell.setColor(Color.BLACK);
			cell.drawRect(column*Board.CELL_LENGTH, row*Board.CELL_LENGTH, Board.CELL_LENGTH, Board.CELL_LENGTH); 
			
		} else {							// else, color in gray with no borders
			cell.setColor(Color.GRAY);
			cell.fillRect(column*Board.CELL_LENGTH, row*Board.CELL_LENGTH, Board.CELL_LENGTH, Board.CELL_LENGTH);
		}
		
		if (this.initial.length() == 2) {	// if the cell is a door
			drawDoor(cell);
		}
	}
	
	public void drawDoor(Graphics cell) {
		
		cell.setColor(Color.BLUE);	// draws a blue rectangle to represent a door
		char door = this.initial.charAt(1);
		
		// how the blue rectangle is orientated is determined by door direction
		if (door == 'L') {
			cell.fillRect(column*Board.CELL_LENGTH, row*Board.CELL_LENGTH, DOOR_DEPTH, Board.CELL_LENGTH);
		} else if (door == 'U') {
			cell.fillRect(column*Board.CELL_LENGTH, row*Board.CELL_LENGTH, Board.CELL_LENGTH, DOOR_DEPTH);
		} else if (door == 'R') {
			cell.fillRect((column*Board.CELL_LENGTH) + Board.CELL_LENGTH - DOOR_DEPTH, row*Board.CELL_LENGTH, DOOR_DEPTH, Board.CELL_LENGTH);
		} else {
			cell.fillRect(column*Board.CELL_LENGTH, (row*Board.CELL_LENGTH) + Board.CELL_LENGTH - DOOR_DEPTH, Board.CELL_LENGTH, DOOR_DEPTH);
		}
	}
	
	public void drawPlayerTargets(Graphics cell) {
		cell.setColor(Color.CYAN);
		cell.fillRect(column*Board.CELL_LENGTH, row*Board.CELL_LENGTH, Board.CELL_LENGTH, Board.CELL_LENGTH);
		cell.setColor(Color.BLACK);
		cell.drawRect(column*Board.CELL_LENGTH, row*Board.CELL_LENGTH, Board.CELL_LENGTH, Board.CELL_LENGTH); 
		
		if (this.initial.length() == 2) {	// if the cell is a door
			drawDoor(cell);
		}
	}

}
