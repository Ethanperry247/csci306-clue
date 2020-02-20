package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	
	private Map<BoardCell, Set<BoardCell>> adjMat;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;

	public IntBoard() {
		adjMat = new HashMap<>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[2][2];
		grid[0][0] = new BoardCell(0,0);
		grid[1][0] = new BoardCell(1,0);
		grid[0][1] = new BoardCell(0,1);
		grid[1][1] = new BoardCell(1,1);
		calcAdjacencies();
	}
	
	public void calcAdjacencies() {
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		
		Set<BoardCell> noCells = new HashSet<BoardCell>();
		
		noCells.add(new BoardCell(0, 0));
		
		return noCells;
	}
	
	public BoardCell getCell(int row, int column) {
		
		return grid[row][column];
		
	}

	public void calcTargets(BoardCell cell, int i) {
		
		targets.add(new BoardCell(0,1));
		targets.add(new BoardCell(1,1));
		targets.add(new BoardCell(1,0));
		targets.add(new BoardCell(0,0));
		
	}

	public Set<BoardCell> getTargets() {

		return targets;
	}
	
	

}
