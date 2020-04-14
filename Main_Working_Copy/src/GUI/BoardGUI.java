package GUI;

import java.awt.Color;
import java.awt.Graphics;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clueGame.*;

public class BoardGUI extends JPanel {
	Board board;
	BoardCell[][] boardCells;
	ArrayList<Player> players;

	public BoardGUI() {
		board = Board.getInstance(); 		// Get the board.	
		boardCells = board.getBoard();		// Get all of the board cells from the board.
		players = board.getPlayerList();	// Get all of the players from the board.
	}
	
	@Override
	public void paintComponent(Graphics graphic) {
		super.paintComponent(graphic);
		
		// Loop through and call the draw method for all board cell.
		for (BoardCell[] row: boardCells) {
			for (BoardCell cell: row) {
				if (cell != null) { // Since some of the cells will be null (since the board array is bigger than it needs to be), do this check first.
					cell.draw(graphic);
				}
			}
		}
		
		// iterates through a set of room initials to draw room names on the board
		for (char initial : board.getInitials()) {
			drawName(initial, graphic, board.getLegend());
		}
		
		// Loop through and call the draw method for all players.
		for (Player player: players) {
			player.draw(graphic);
		}
		
		if (board.currentPlayer() instanceof HumanPlayer) {
			drawTargets(board.getTargets(), graphic);
		}
		
	}
	
	// method to draw room names
	public void drawName(char initial, Graphics cell, Map<Character, ArrayList<String>> legend) {
		
		// retrieves row and column values for the location of the room name
		String name = legend.get(initial).get(0);	
		int row = Integer.parseInt(legend.get(initial).get(1));	
		int column = Integer.parseInt(legend.get(initial).get(2));
		
		// colors in the doors
		cell.setColor(Color.BLUE);
		cell.drawString(name, column*25, row*25);
	}
	
	public void drawTargets(Set<BoardCell> targets, Graphics cell) {
		for (BoardCell target : targets) {
			target.drawPlayerTargets(cell);
		}
	}

}
