package clueGame;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Board {
	
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		
		// Initialize every row and column in board to a walkway.
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				board[row][col] = new BoardCell(numRows, numColumns, 'w');
			}
		}
		
		legend = new HashMap<Character, String>();
		
	}
	
	// To be implemented...
	public void loadRoomConfig() {
		
	}
	
	// To be implemented...
	public void loadBoardConfig() {
		
	}
	
	// To be implemented...
	public void calcAdjacencies() {
		
	}
	
	// To be implemented...
	public void calcTargets() {
		
	}
	
	public void setConfigFiles(String boardConfig, String roomConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = roomConfig;	
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public Map<Character, String> getLegend() {
		return legend;
	}
	
	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}
	
	
	
	

}
