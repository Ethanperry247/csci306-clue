package experiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {

	private Map<BoardCell, Set<BoardCell>> adjMat;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	private int gridLength;

	public IntBoard(int gridLength) {
		adjMat = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		this.gridLength = gridLength;
		createGrid();
		calcAdjacencies();
	}

	public void createGrid() {

		grid = new BoardCell[gridLength][gridLength];

		// Populate the grid with new boardcells.
		for (int i = 0; i < gridLength; i++) {
			for (int j = 0; j < gridLength; j++) {
				BoardCell newCell = new BoardCell(i, j);
				grid[i][j] = newCell;
			}
		}

	}

	public void calcAdjacencies() {

		for (int i = 0; i < gridLength; i++) {
			for (int j = 0; j < gridLength; j++) {

				// There are 9 nonets to this board:
				// four edges, four corners, and the center.
				// Accordingly, there are 9 checks.

				Set<BoardCell> adjCells;

				if (i == 0 && j == 0) { // Upper left corner.

					// This is a hashset declaration that includes the cells of interest.
					adjCells = new HashSet<BoardCell>(Arrays.asList(grid[i + 1][j], grid[i][j + 1]));

				} else if (i == 0 && j == gridLength - 1) { // Lower Left Corner.

					adjCells = new HashSet<BoardCell>(Arrays.asList(grid[i + 1][j], grid[i][j - 1]));

				} else if (i == gridLength - 1 && j == gridLength - 1) { // Lower right corner.

					adjCells = new HashSet<BoardCell>(Arrays.asList(grid[i - 1][j], grid[i][j - 1]));

				} else if (i == gridLength - 1 && j == 0) { // Upper left corner.

					adjCells = new HashSet<BoardCell>(Arrays.asList(grid[i - 1][j], grid[i][j + 1]));

				} else if (i == 0 && j != 0 && j != gridLength - 1) { // Middle left nonet.

					adjCells = new HashSet<BoardCell>(Arrays.asList(grid[i + 1][j], grid[i][j - 1], grid[i][j + 1]));

				} else if (i == gridLength - 1 && j != 0 && j != gridLength - 1) { // Middle right nonet.

					adjCells = new HashSet<BoardCell>(Arrays.asList(grid[i - 1][j], grid[i][j + 1], grid[i][j - 1]));

				} else if (j == 0 && i != 0 && i != gridLength - 1) { // Upper middle nonet.

					adjCells = new HashSet<BoardCell>(Arrays.asList(grid[i + 1][j], grid[i - 1][j], grid[i][j + 1]));

				} else if (j == gridLength - 1 && i != 0 && i != gridLength - 1) { // Lower Middle nonet.

					adjCells = new HashSet<BoardCell>(Arrays.asList(grid[i + 1][j], grid[i - 1][j], grid[i][j - 1]));

				} else { // The middle section.

					adjCells = new HashSet<BoardCell>(
							Arrays.asList(grid[i + 1][j], grid[i - 1][j], grid[i][j + 1], grid[i][j - 1]));

				}

				// Adds the cell and its appropriate adjacent cells.
				adjMat.put(grid[i][j], adjCells);

			}
		}
		
	}

	public Set<BoardCell> getAdjList(BoardCell cell) {

		return adjMat.get(cell);
	}

	public BoardCell getCell(int row, int column) {

		return grid[row][column];

	}

	public void calcTargets(BoardCell cell, int i) {
		
		if (visited.contains(cell)) {
			return;
		} else {
			visited.add(cell);
		}
		
		if (i == 0) {
			targets.add(cell);
			visited.remove(cell);
			return;
		} else {
			for (BoardCell adjCell : adjMat.get(cell)) {
				int j = i - 1;
				calcTargets(adjCell, j);
				
			}
			visited.remove(cell);
		}

	}

	public Set<BoardCell> getTargets() {

		return targets;
	}

}
