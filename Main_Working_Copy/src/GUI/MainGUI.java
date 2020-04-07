// Authors: Ethan Perry and Caleb Pan

package GUI;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class MainGUI extends JFrame {
	private ControlGUI controlGUI;
	private FileMenu menuBar;
	private MyCardsGUI myCardsGUI;
	private BoardGUI boardGUI;

	public MainGUI(String name) {
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
		
	}

}
