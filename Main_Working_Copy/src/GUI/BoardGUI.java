package GUI;

import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import clueGame.*;

public class BoardGUI extends JPanel{
	Board board;
	BoardCell[][] boardCells;
	ArrayList<Player> players;

	public BoardGUI() {
		board = Board.getInstance(); // Get the board.
		JLabel label = new JLabel("Board"); // Draw a label for the board.
		add(label);
		boardCells = board.getBoard(); // Get all of the board cells from the board.
		players = board.getPlayerList(); // Get all of the players from the board.
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Loop through and call the draw method for all board cell.
		for (BoardCell[] row: boardCells) {
			for (BoardCell cell: row) {
				cell.draw(g);
			}
		}
		
		for (Player player: players) {
			player.draw();
		}
		
		
		
	}

}
