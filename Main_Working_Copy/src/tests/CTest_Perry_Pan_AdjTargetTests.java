// Authors: Caleb Pan, Ethan Perry

package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class CTest_Perry_Pan_AdjTargetTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt", "CluePlayers.txt", "ClueWeapons.txt");		
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are GREEN on the planning spreadsheet
	// fulfills requirement for locations within rooms 
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test bottom left corner of board in Dungeon room
		Set<BoardCell> testList = board.getAdjList(19, 19);
		assertEquals(0, testList.size());
		// Test cell in room in Greenhouse
		testList = board.getAdjList(7, 1);
		assertEquals(0, testList.size());
		// Test cell in room on top edge
		testList = board.getAdjList(0, 11);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the walkway. 
	// These tests are CYAN on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY DOWN
		Set<BoardCell> testList = board.getAdjList(4, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(5, 6)));
		// TEST DOORWAY RIGHT
		testList = board.getAdjList(8, 3);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 4)));
		//TEST DOORWAY UP
		testList = board.getAdjList(14, 14);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(13, 14)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are PURPLE in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		// test is on bottom edge of the board
		Set<BoardCell> testList = board.getAdjList(19, 4);
		assertTrue(testList.contains(board.getCellAt(19, 5)));
		assertTrue(testList.contains(board.getCellAt(18, 4)));
		assertTrue(testList.contains(board.getCellAt(19, 3)));
		assertEquals(3, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(5, 6);
		assertTrue(testList.contains(board.getCellAt(6, 6)));
		assertTrue(testList.contains(board.getCellAt(5, 7)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(4, 6)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(2, 9);
		assertTrue(testList.contains(board.getCellAt(3, 9)));
		assertTrue(testList.contains(board.getCellAt(1, 9)));
		assertTrue(testList.contains(board.getCellAt(2, 8)));
		assertTrue(testList.contains(board.getCellAt(2, 10)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		// test is on left edge of the board
		testList = board.getAdjList(13, 0);
		assertTrue(testList.contains(board.getCellAt(12, 0)));
		assertTrue(testList.contains(board.getCellAt(14, 0)));
		assertTrue(testList.contains(board.getCellAt(13, 1)));
		assertEquals(3, testList.size());
		
		// requirement for locations that are adjacent to a doorway with needed direction fullfiled 
		// all four directions are also tested
	}

	// Test a variety of walkway scenarios
	// These tests are WHITE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on right edge of board and next to Cellar room, two walkway pieces
		Set<BoardCell> testList = board.getAdjList(6, 19);
		assertTrue(testList.contains(board.getCellAt(5, 19)));
		assertTrue(testList.contains(board.getCellAt(6, 18)));
		assertEquals(2, testList.size());
		
		// Test on top edge of board and next to Studio room, two walkway pieces
		// requirement for test locations at each edge of the board is fulfilled
		// requirement for test locations that are beside a room cell that is not a doorway is fulfilled
		testList = board.getAdjList(0, 8);
		assertTrue(testList.contains(board.getCellAt(0, 9)));
		assertTrue(testList.contains(board.getCellAt(1, 8)));
		assertEquals(2, testList.size());

		// Test with only walkways as adjacent locations
		testList = board.getAdjList(14,3);
		assertTrue(testList.contains(board.getCellAt(14, 2)));
		assertTrue(testList.contains(board.getCellAt(14, 4)));
		assertTrue(testList.contains(board.getCellAt(13, 3)));
		assertTrue(testList.contains(board.getCellAt(15, 3)));
		assertEquals(4, testList.size());
		
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board and beside room
	// Have already tested adjacency lists on all four edges
	// These are ORANGE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(19, 10, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 11))); // door entry
		assertTrue(targets.contains(board.getCellAt(19, 9)));
		assertTrue(targets.contains(board.getCellAt(18, 10)));
		
		board.calcTargets(5, 15, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 15)));	// door entry
		assertTrue(targets.contains(board.getCellAt(6, 15)));	
		assertTrue(targets.contains(board.getCellAt(5, 16)));
		assertTrue(targets.contains(board.getCellAt(5, 14)));
		
		// fulfills targets that allow the user to enter a room requirement
	}
	
	// Tests of just walkways, 2 steps
	// These are ORANGE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(0, 3, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(2, 3)));
		assertTrue(targets.contains(board.getCellAt(1, 4)));
		
		board.calcTargets(4, 0, 2);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 2)));
		assertTrue(targets.contains(board.getCellAt(5, 1)));				
	}
	
	// Tests of just walkways, 3 steps
	// These are ORANGE on the planning spreadsheet
	@Test
	public void testTargetsThreeSteps() {
		board.calcTargets(19, 16, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 16)));
		assertTrue(targets.contains(board.getCellAt(17, 15)));
		assertTrue(targets.contains(board.getCellAt(16, 16)));
		assertTrue(targets.contains(board.getCellAt(19, 15)));
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are ORANGE on the planning spreadsheet

	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(13, 19, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 18)));
		assertTrue(targets.contains(board.getCellAt(13, 17)));	
		assertTrue(targets.contains(board.getCellAt(12, 16)));	
		assertTrue(targets.contains(board.getCellAt(13, 15)));	
		assertTrue(targets.contains(board.getCellAt(14, 16)));	
	}	
	// fulfills targets along walkways, at various distances requirement (4 distances)

	
	// Test getting out of a room
	// These are ORANGE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(14, 7, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 7)));
		// Take two steps
		board.calcTargets(14, 7, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 7)));
		assertTrue(targets.contains(board.getCellAt(13, 8)));
		assertTrue(targets.contains(board.getCellAt(13, 6)));
		
		// fulfills test locations that are doorways requirement 
		// fulfills targets calculated when leaving a room requirement 
	}

}
