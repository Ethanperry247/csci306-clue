// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class BoardCell {
	
	private int row;
	private int column;
	private String initial;
	private int pixelLength;
	private int pixelHeight;
	private int pixelRow;
	private int pixelColumn;
	

	public BoardCell(int row, int column, String initial) {
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.pixelLength = 25;
		this.pixelHeight = 25;
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
	
	public void draw(Graphics cell) {
		if (this.initial.equals("W")) {
			cell.setColor(Color.YELLOW);
			cell.fillRect(column*25, row*25, 25, 25);
			cell.setColor(Color.BLACK);
			cell.drawRect(column*25, row*25, 25, 25); 
			
		} else {
			cell.setColor(Color.GRAY);
			cell.fillRect(column*25, row*25, 25, 25);
			
		}
		
		if (this.initial.length() == 2) {
			cell.setColor(Color.BLUE);
			char door = this.initial.charAt(1);
			if (door == 'L') {
				cell.fillRect(column*25, row*25, 5, 25);
			} else if (door == 'U') {
				cell.fillRect(column*25, row*25, 25, 5);
			} else if (door == 'R') {
				cell.fillRect((column*25) + 20, row*25, 5, 25);
			} else {
				cell.fillRect(column*25, (row*25) + 20, 25, 5);
			}
			
		}
	}
	

}
