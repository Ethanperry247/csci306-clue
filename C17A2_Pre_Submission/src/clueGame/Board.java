package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	
	private int numRows;
	private int numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private String boardConfigFile;
	private String roomConfigFile;
	private Set<BoardCell> visited = new HashSet<BoardCell>();	// set to keep track of cells already visited in a turn
	private Set<BoardCell> targets = new HashSet<BoardCell>();	// set to keep track of target cells
	// map with keys as cells on the board and a set of the cells adjacent to the key cell for the values 
	private Map<BoardCell, Set<BoardCell>> adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {}
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
		
		// Reloading the legend to assure that there is a new legend every time a new config file is loaded.
		legend = new HashMap<Character, String>();
		
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
		
		// Reloading the board matrix to ensure that a new board is loaded each time loadBoardConfig is called.
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		
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
		
		scanner.close(); // Protect resources
	}
	
//////////////////////////////////////////////////////////////////////////////////////
	
	public void calcAdjacencies() {

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				Set<BoardCell> adjCells = new HashSet<BoardCell>(getAdjList(i,j));	// finds all adjacent cells that can be entered
				adjMatrix.put(board[i][j], adjCells);	// Adds the cell and its appropriate adjacent cells.
			}
		}
	}
	

	public void calcTargets(int i, int j, int k) {
		
		calcAdjacencies();
		BoardCell cell = getCellAt(i,j); 
		
		if (visited.contains(cell)) {	// if the cell is already visited, return
			return;
		} else {
			visited.add(cell);	// else, add to set off cells visited
		}
		
		if (k == 0 || cell.isDoorway()) {	// if there are no moves remaining or at a door cell
			if (visited.size() == 1) {	// if starting at a door cell
				for (BoardCell adjCell : adjMatrix.get(cell)) {	// loops through adjacent cells 
					int movesRemaining = k - 1;	// decrements moves left
					calcTargets(adjCell.getRow(), adjCell.getColumn(), movesRemaining);	// recursive call with decremented moves left 
				}
				visited.remove(cell);	// Remove the door, as it is not a valid target when starting on the door.
			} else { 
				targets.add(cell);
			}
			visited.remove(cell);
			return;
		} else {
			for (BoardCell adjCell : adjMatrix.get(cell)) {
				int movesRemaining = k - 1;
				calcTargets(adjCell.getRow(), adjCell.getColumn(), movesRemaining);	// Recur on all adjacent cells.
			}
			visited.remove(cell);
		}
	}
	
	public Set<BoardCell> getTargets() {
		Set<BoardCell> temp = new HashSet<BoardCell>(targets);	// makes copy of target cells
		// clear sets for future use of CalcTargets
		targets.clear();
		visited.clear();	
		return temp;	// returns preserved copy of target cells
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
	
	public Set<BoardCell> getAdjList(int i, int j) {
		BoardCell current = getCellAt(i,j);
		Set<BoardCell> adjacent = new HashSet<BoardCell>(); 
	
		if (current.isRoom()) {	// if in room, cannot move
			return adjacent;
			
		} else if (current.isDoorway()) {	// if on a door cell, finds adjacent cell to enter
			
			switch(current.getDoorDirection()) {
				case LEFT: 
					adjacent.add(getCellAt(i,j-1));
					break;
				case RIGHT:
					adjacent.add(getCellAt(i,j+1));
					break;
				case UP:
					adjacent.add(getCellAt(i-1,j));
					break;
				case DOWN:
					adjacent.add(getCellAt(i+1,j));
					break;
				default:
					break;
			} 
			
		} else if (current.isWalkway()) {	// if on a walkway, check all adjacent cells if can move onto (other walkways or doors) 
			
			if (j > 0 && getCellAt(i,j-1).getInitial() == 'W' 
					|| j > 0 && getCellAt(i,j-1).getDoorDirection() == DoorDirection.RIGHT) {
				adjacent.add(getCellAt(i,j-1));
			}
			
			if (j < numColumns-1 && getCellAt(i,j+1).getInitial() == 'W'
					|| j < numColumns-1 && getCellAt(i,j+1).getDoorDirection() == DoorDirection.LEFT) {
				adjacent.add(getCellAt(i,j+1));
			}
			
			if (i > 0 && getCellAt(i-1,j).getInitial() == 'W'
					|| i > 0 && getCellAt(i-1,j).getDoorDirection() == DoorDirection.DOWN) {
				adjacent.add(getCellAt(i-1,j));
			}
			
			if (i < numRows-1 && getCellAt(i+1,j).getInitial() == 'W'
					|| i < numRows-1 && getCellAt(i+1,j).getDoorDirection() == DoorDirection.UP) {
				adjacent.add(getCellAt(i+1,j));
			}
			
		}
		
		return adjacent;
	}	

//////////////////////////////////////////////////////////////////////////////////////

}