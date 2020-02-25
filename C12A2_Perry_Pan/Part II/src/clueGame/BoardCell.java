package clueGame;

public class BoardCell {
	
	private int row;
	private int column;

	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public String toString() {
		return "BoardCell [row=" + row + ", column=" + column + "]";
	}
	
	

}
