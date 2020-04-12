// Authors: Ethan Perry and Caleb Pan

package GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import clueGame.Board;
import clueGame.Player;

public class MainGUI extends JFrame {
	private ControlGUI controlGUI;
	private FileMenu menuBar;
	private MyCardsGUI myCardsGUI;
	private BoardGUI boardGUI;
	private Board board;
	private Player player;

	public MainGUI(String name) {
		board = Board.getInstance();
		player = board.currentPlayer();
		// Create a JFrame with all the normal functionality
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(name);
		setSize(1000, 800); // Set the desired size
		controlGUI = new ControlGUI(); // Add a control GUI to the bottom of the GUI.
		add(controlGUI, BorderLayout.SOUTH); // Put the JPanel in the center
		menuBar = new FileMenu(); // Create and set a menu bar for our game.
		setJMenuBar(menuBar);
		myCardsGUI = new MyCardsGUI();
		add(myCardsGUI, BorderLayout.EAST);
		boardGUI = new BoardGUI();
		add(boardGUI, BorderLayout.CENTER);
		setVisible(true); // Reveal the JFrame
		
		// Display the opening dialog.
		openingDialog();
		
	}
	
	// Opening dialog will display the name of the human (first) player.
	public void openingDialog() {
		String message = "You are " + player.getName() + ", Press Next Player to begin play."; 
		JOptionPane.showMessageDialog(null, message);
	}

}
