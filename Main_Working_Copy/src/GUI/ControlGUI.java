// Authors: Ethan Perry and Caleb Pan

package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Player;

public class ControlGUI extends JPanel {
	public static final int WIDTH = 900; // Controls the size of the control GUI.
	public static final int HEIGHT = 200; // Kind of an attempt at responsive designing.
	private Board board;
	private Player currentPlayer;

	public ControlGUI() {
		board = Board.getInstance();
		currentPlayer = board.currentPlayer();
		// Create a layout with 2 rows and 3 columns
		setLayout(new GridLayout(2,0));
		JPanel panel = createNamePanel("Whose Turn?"); // Display of whose turn it is.
		add(panel);
		panel = createButtonPanel("Next Player"); // Button to move to the next player.
		add(panel);
		panel = createButtonPanel("Make an Accusation"); // Button to make an accusation.
		add(panel);
		panel = createEtchedNamePanel("Die", "Roll", false, HEIGHT/3, WIDTH/8); // Display the roll of the die.
		add(panel);
		panel = createEtchedNamePanel("Guess", "Guess", true, HEIGHT/3, WIDTH/3); // Display the guesses made by other players.
		add(panel);
		panel = createEtchedNamePanel("Guess Result", "Response", false, HEIGHT/3, WIDTH/5); // Display the guess result.
		add(panel);
	}
	
	
	// Creates a panel with two rows filled with a label and a text field.
	private JPanel createNamePanel(String label) {
		// Create an outer panel to house a different inner panel.
		// This is designed so that we can change the size of elements while still using a grid layout.
		JPanel outerPanel = new JPanel(); 
		JPanel panel = new JPanel(new BorderLayout());
		// Use a grid layout, 2 rows, 2 column (label, text)
		panel.setLayout(new GridLayout(2,1)); // Two rows, one column
	 	JLabel nameLabel = new JLabel(label);
	 	nameLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label.
	 	JTextField name = new JTextField(currentPlayer.getName());
		name.setEditable(false); // Disable editing of the text field.
		panel.add(nameLabel);
		// panel.setPreferredSize(new Dimension(10, 10)); // Set Dimensions if desired.
		panel.add(name);
		outerPanel.add(panel);
		return outerPanel;
	}

	// Create a new etched border with a name, containing a label and text field.
	private JPanel createEtchedNamePanel(String outerLabel, String innerLabel, boolean verticleGridLayout, int height, int width) {
		// Create an outer panel to house a different inner panel.
		// This is designed so that we can change the size of elements while still using a grid layout.
		JPanel outerPanel = new JPanel();  	
		JPanel panel = new JPanel(new BorderLayout());
	 	// Use a grid layout, 1 row, 2 elements (label, text)
	 	if (verticleGridLayout) { // If verticleGridLayout is true, there will be two rows, one column. 
	 		panel.setLayout(new GridLayout(2, 1));
	 	} else { // Otherwise, one column and two rows.
	 		panel.setLayout(new GridLayout(1, 2));
	 	}
	 	JLabel nameLabel = new JLabel(innerLabel); // Set the name of the inner label.
	 	JTextField name = new JTextField(20);
		name.setEditable(false); // Disable editing of the text field.
		panel.setPreferredSize(new Dimension(width, height)); // Set Dimensions if desired.
		panel.add(nameLabel);
		panel.add(name);
		panel.setBorder(new TitledBorder (new EtchedBorder(), outerLabel)); // Set the etched border with a title.
		outerPanel.add(panel);
		return outerPanel;
	}
	 
	private JPanel createButtonPanel(String name) {
		// Create a new button, add to a new panel, and return.
		JButton button = new JButton(name);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(button);
		return panel;
	}

}
