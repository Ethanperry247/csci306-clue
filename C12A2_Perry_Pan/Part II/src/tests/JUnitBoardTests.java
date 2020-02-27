package tests;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class JUnitBoardTests {
	
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_ROWS = 20;
	public static final int NUM_COLUMNS = 20;

	public JUnitBoardTests() {
		
	}
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use config files
		board.setConfigFiles("ClueBoardLayout.csv", "ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Greenhouse", legend.get('G'));
		assertEquals("Dungeon", legend.get('D'));
		assertEquals("Studio", legend.get('S'));
		assertEquals("Cellar", legend.get('C'));
		assertEquals("Atrium", legend.get('A'));
		assertEquals("Veranda", legend.get('V'));
		assertEquals("Lounge", legend.get('L'));
		assertEquals("Parlor", legend.get('P'));
		assertEquals("Office", legend.get('O'));
		assertEquals("Walkway", legend.get('W'));
		assertEquals("Closet", legend.get('X'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(2, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(6, 4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(15, 9);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(14, 14);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(17, 9);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(14, 9);
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		// Loop through and count all doors. Then compare that to assure there are 13.
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++) {
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		}
		Assert.assertEquals(13, numDoors);
	}
	
	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		// Test cells in assorted room corners.
		assertEquals('V', board.getCellAt(0, 0).getInitial());
		assertEquals('L', board.getCellAt(19, 0).getInitial());
		assertEquals('A', board.getCellAt(0, 19).getInitial());
		assertEquals('D', board.getCellAt(19, 19).getInitial());
		// Test three walkways.
		assertEquals('W', board.getCellAt(0, 3).getInitial());
		assertEquals('W', board.getCellAt(9, 5).getInitial());
		assertEquals('W', board.getCellAt(14, 12).getInitial());
		// Test the closet in several locations.
		assertEquals('X', board.getCellAt(9,10).getInitial());
		assertEquals('X', board.getCellAt(6,7).getInitial());
		assertEquals('X', board.getCellAt(11,11).getInitial());
		assertEquals('X', board.getCellAt(12,7).getInitial());
	}

}
