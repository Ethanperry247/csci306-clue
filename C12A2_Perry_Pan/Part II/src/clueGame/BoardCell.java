package clueGame;

public class BoardCell {
	
	private int row;
	private int column;
	private char initial;

	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
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
	
	public boolean getInitial() {
		return false;
	}
	
	

}
