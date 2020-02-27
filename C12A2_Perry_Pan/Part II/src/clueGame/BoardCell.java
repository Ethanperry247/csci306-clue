package clueGame;

public class BoardCell {
	
	private int row;
	private int column;
	private String initial;

	public BoardCell(int row, int column, String initial) {
		this.row = row;
		this.column = column;
		this.initial = initial;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
	public boolean isDoorway() {
		if (this.initial.length() == 1) {	// returns false if cell does not contain door
			return false;
		} else {
			return true;
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
		// if statement to confirm the cell is not a door, walkway, or closet 
		if (!this.initial.equals("W") && !this.initial.equals("X") && this.initial.length() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public DoorDirection getDoorDirection() {
		
		if (this.initial.length() == 1) {	// returns null if cell has no door
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
	
	public String getInitial() {
		return initial;
	}
	
	

}
