package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import experiment.BoardCell;
import experiment.IntBoard;

public class IntBoardTests {
	
	IntBoard board = new IntBoard();

	public IntBoardTests() {
		
	}
	
	@Before
    public void beforeAll() {
       board = new IntBoard();  
    }
	
	// Creates a new board cell and tests what are supposed to be its targets.
	// Tests will all fail because calcTargets is not implemented.
	@Test
	public void testTargets() { 
		
			// Tests for a roll of three.
			BoardCell cell = board.getCell(0, 0);
			board.calcTargets(cell, 3);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(3, 0)));
			assertTrue(targets.contains(board.getCell(2, 1)));
			assertTrue(targets.contains(board.getCell(0, 1)));
			assertTrue(targets.contains(board.getCell(1, 2)));
			assertTrue(targets.contains(board.getCell(0, 3)));
			assertTrue(targets.contains(board.getCell(1, 0)));
			
			// Tests for a roll of two.
			board = new IntBoard();
			board.calcTargets(cell, 2);
			targets = board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(2, 0)));
			assertTrue(targets.contains(board.getCell(1, 1)));
			assertTrue(targets.contains(board.getCell(0, 2)));
			
			// Tests for a roll of two and a starting position of (1, 1).
			board = new IntBoard();
			cell = board.getCell(1, 1);
			board.calcTargets(cell, 2);
			targets = board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(0, 0)));
			assertTrue(targets.contains(board.getCell(2, 2)));
			assertTrue(targets.contains(board.getCell(0, 2)));
			assertTrue(targets.contains(board.getCell(2, 0)));
			assertTrue(targets.contains(board.getCell(1, 3)));
			assertTrue(targets.contains(board.getCell(3, 1)));
			
		
	}
	
	// Creates new board cells and tests their adjacent cells.
	// Test will fail because the adjacency matrix creator isn't implemented.
	@Test
	public void testAdjacencies()
	{
		// Top Left Corner
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
		
		// Top Right corner
		cell = board.getCell(0,2);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
		
		// Bottom Left Corner
		cell = board.getCell(2,0);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(2, testList.size());
		
		// Bottom Right Corner
		cell = board.getCell(2,2);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertEquals(2, testList.size());
		
		// Top Middle Edge
		cell = board.getCell(0,1);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertEquals(3, testList.size());
		
		// Bottom Middle Edge
		cell = board.getCell(2,1);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertEquals(3, testList.size());
		
		// Middle Left Edge
		cell = board.getCell(1,0);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 0)));
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertEquals(3, testList.size());
		
		// Middle Right Edge
		cell = board.getCell(1,2);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 2)));
		assertTrue(testList.contains(board.getCell(0, 2)));
		assertTrue(testList.contains(board.getCell(1, 1)));
		assertEquals(3, testList.size());
		
		// Center
		cell = board.getCell(1,1);
		testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 1)));
		assertEquals(4, testList.size());
		
		
		
	}

}
