package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		// Board and legend must be loaded in order to run the loading meth
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		legend = new HashMap<Character, String>();
		
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		
		for (int i = 0; i < MAX_BOARD_SIZE; i++) {
			for (int j = 0; j < MAX_BOARD_SIZE; j++) {
				board[i][j] = new BoardCell(0, 0, "W");
			}
		}
		
		legend.put('W', "Example Value");
		
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
