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
	
	@Test
	public void testTargets() {
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
		
	}
	
	@Test
	public void testAdjacencies()
	{
		BoardCell cell = board.getCell(0,0);
		Set<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
		assertEquals(4, testList.size());
		assertEquals(6, testList.size());
	}

}
