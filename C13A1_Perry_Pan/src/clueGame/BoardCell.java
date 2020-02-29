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
		return false;
	}
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public DoorDirection getDoorDirection() {
		return null;
	}
	
	public char getInitial() {
		return initial.charAt(0);
	}
	
	

}
