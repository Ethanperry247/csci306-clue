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
		int numColumns = 0;
		int row = 0;	// Will be incremented as elements are added to grid.

		// This first if statement will get the length of the first column to be compared against all others.
		if (scanner.hasNextLine()) {
			// Grab a line from the file.
			String line = scanner.nextLine();

			// Split that line into an array.
			String[] values = line.split(",");

			// Assume every row should be the same length as the first row. Otherwise, exceptions will be thrown.
			numColumns = values.length;

			for (int column = 0; column < values.length; column++) {
				if (legend.get(values[column].charAt(0)) == null) {
					// If any member of the board config file has a different initial than in the legend, throw an exception.
					scanner.close();
					throw new BadConfigFormatException("Invalid board cell in board config file.");
				}
				board[row][column] = new BoardCell(row, column, values[column]);
			}
			row++;
		}
		// Loop through and populate the rest of board grid.
		while (scanner.hasNextLine()) {

			// Grab a line from the file.
			String line = scanner.nextLine();

			// Split that line into an array.
			String[] values = line.split(",");

			// If a row is found to have an inconsistent length from any other, throw an exception.
			if (values.length != numColumns) {
				scanner.close();
				throw new BadConfigFormatException("Inconsistant row length in board config file.");
			}

			for (int column = 0; column < values.length; column++) {

				// If any member of the board config file has a different initial than in the legend, throw an exception.
				if (legend.get(values[column].charAt(0)) == null) {
					scanner.close();
					throw new BadConfigFormatException("Invalid board cell in board config file.");
				}

				board[row][column] = new BoardCell(row, column, values[column]);
			}
			row++;
		}

		numRows = row;
		this.numColumns = numColumns;

		scanner.close(); // Protect resources
	}


	public void calcAdjacencies() {

		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numColumns; column++) {
				Set<BoardCell> adjCells = new HashSet<BoardCell>(getAdjList(row,column));	// finds all adjacent cells that can be entered
				adjMatrix.put(board[row][column], adjCells);	// Adds the cell and its appropriate adjacent cells.
			}
		}
	}

	public void calcTargets(int row, int column, int moves) {

		calcAdjacencies();
		BoardCell cell = getCellAt(row,column); 

		if (visited.contains(cell)) {	// if the cell is already visited, return
			return;
		} else {
			visited.add(cell);	// else, add to set of cells visited
		}

		if (moves == 0 || cell.isDoorway()) {										// if there are no moves remaining or at a door cell
			if (visited.size() == 1) {												// if starting at a door cell
				for (BoardCell adjCell : adjMatrix.get(cell)) {						// loops through adjacent cells 
					calcTargets(adjCell.getRow(), adjCell.getColumn(), moves-1);	// recursive call with decremented moves left 
				}
				visited.remove(cell);	// Remove the door, as it is not a valid target when starting on the door.
			} else { 
				targets.add(cell);
			}
			visited.remove(cell);
			return;
		} else {
			for (BoardCell adjCell : adjMatrix.get(cell)) {
				calcTargets(adjCell.getRow(), adjCell.getColumn(), moves-1);	// recursive call with decremented moves left 
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


	public void setConfigFiles(String boardConfig, String roomConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = roomConfig;	
	}

	
	// getters for number of rows, columns, legend, and cell at a specific location are all below
	
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Map<Character, String> getLegend() {
		return legend;
	}

	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}


	public Set<BoardCell> getAdjList(int row, int column) {
		BoardCell current = getCellAt(row,column);
		Set<BoardCell> adjacent = new HashSet<BoardCell>(); 

		if (current.isRoom()) {	// if in room, cannot move
			return adjacent;

		} else if (current.isDoorway()) {	// if on a door cell, finds adjacent cell to enter

			switch(current.getDoorDirection()) {
			case LEFT:	// check if can enter to the left cell
				adjacent.add(getCellAt(row, column-1));
				break;
			case RIGHT:	// check if can enter to the right cell
				adjacent.add(getCellAt(row, column+1));
				break;
			case UP:	// check if can enter to the cell above
				adjacent.add(getCellAt(row-1, column));
				break;
			case DOWN:	// check if can enter to the cell below
				adjacent.add(getCellAt(row+1, column));
				break;
			default:
				break;
			} 

		} else if (current.isWalkway()) {	// if on a walkway, check all adjacent cells if can move onto (other walkways or doors) 

			// check if can move to the cell to the right
			if (column > 0 && (getCellAt(row, column-1).isWalkway()
					|| getCellAt(row, column-1).getDoorDirection() == DoorDirection.RIGHT)) {
				adjacent.add(getCellAt(row, column-1));
			}
			
			// check if can move to the cell to the left
			if (column < numColumns-1 && (getCellAt(row, column+1).isWalkway()
					|| getCellAt(row, column+1).getDoorDirection() == DoorDirection.LEFT)) {
				adjacent.add(getCellAt(row, column+1));
			}
			
			// check if can move to the cell below
			if (row > 0 && (getCellAt(row-1, column).isWalkway()
					|| getCellAt(row-1, column).getDoorDirection() == DoorDirection.DOWN)) {
				adjacent.add(getCellAt(row-1, column));
			}
			
			// check if can move to the cell above
			if (row < numRows-1 && (getCellAt(row+1, column).isWalkway()
					|| getCellAt(row+1, column).getDoorDirection() == DoorDirection.UP)) {
				adjacent.add(getCellAt(row+1, column));
			}

		}

		return adjacent;	// return all adjacent cells that can be moved onto from current board cell
	}	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// all to be implemented
	
	public void loadConfigFiles() {
		
	}
	
	public void selectAnswer() {
		
	}
	
	//will have TBD parameter
	public Card handleSuggestion() {
		Card x = new Card("x");
		return x;
	}
	
	public boolean checkAccusation(Solution accusation) {
		return false;
	}
	
}