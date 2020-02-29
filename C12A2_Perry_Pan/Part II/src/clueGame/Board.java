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
		
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void loadRoomConfig() throws BadConfigFormatException, FileNotFoundException {
		
		// Scanner will take in the room config file and parse it into the legend map.
		Scanner scanner = new Scanner(new File(roomConfigFile));
		while (scanner.hasNextLine()) {
			// Grab a line from the file.
			String row = scanner.nextLine();
			
			// Split that line into an array (will be length three for the three fields of the file.)
			String[] values = row.split(", ");
			
			// Put that into the legend.
			legend.put(values[0].charAt(0), values[1]);
			
			// Make sure that the item is either a card or other, otherwise throw an exception.
			if (!values[2].equals("Card") && !values[2].equals("Other")) {
				scanner.close();
				throw new BadConfigFormatException("Incorrect room type in room config file.");
			}
		
		}
		
		scanner.close();
	}
	
	public void loadBoardConfig() throws BadConfigFormatException, FileNotFoundException {
		Scanner scanner = new Scanner(new File(boardConfigFile));
		int rowLength = 0;
		int r = 0; // Will be incremented as elements are added to grid.
		
		// This first if statement will get the length of the first column to be compared against all others.
		if (scanner.hasNextLine()) {
			// Grab a line from the file.
			String row = scanner.nextLine();
			
			// Split that line into an array.
			String[] values = row.split(",");
			
			// Assume every row should be the same length as the first row. Otherwise, exceptions will be thrown.
			rowLength = values.length;
			
			for (int c = 0; c < values.length; c++) {
				if (legend.get(values[c].charAt(0)) == null) {
					// If any member of the board config file has a different initial than in the legend, throw an exception.
					scanner.close();
					throw new BadConfigFormatException("Invalid board cell in board config file.");
				}
				board[r][c] = new BoardCell(r, c, values[c]);
			}
			r++;
		}
		// Loop through and populate the rest of board grid.
		while (scanner.hasNextLine()) {
			
			// Grab a line from the file.
			String row = scanner.nextLine();
			
			// Split that line into an array.
			String[] values = row.split(",");
			
			// If a row is found to have an inconsistent length from any other, throw an exception.
			if (values.length != rowLength) {
				scanner.close();
				throw new BadConfigFormatException("Inconsistant row length in board config file.");
			}
			
			for (int c = 0; c < values.length; c++) {

				// If any member of the board config file has a different initial than in the legend, throw an exception.
				if (legend.get(values[c].charAt(0)) == null) {
					scanner.close();
					throw new BadConfigFormatException("Invalid board cell in board config file.");
				}
				
				board[r][c] = new BoardCell(r, c, values[c]);
			}
			r++;
		}
		
		numRows = r;
		numColumns = rowLength;
		
		scanner.close();
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	// To be implemented...
	public void calcAdjacencies() {
		
	}
	
	// To be implemented...
	public void calcTargets(int i, int j, int k) {
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////	
	
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
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return null;
	}
	public Set<BoardCell> getAdjList(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}	
	
	//////////////////////////////////////////////////////////////////////////////////////

}
