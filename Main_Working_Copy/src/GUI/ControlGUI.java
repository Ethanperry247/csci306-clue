// Authors: Ethan Perry and Caleb Pan

package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

public class ControlGUI extends JPanel {
	public static final int WIDTH = 900; // Controls the size of the control GUI.
	public static final int HEIGHT = 200; // Kind of an attempt at responsive designing.
	private Board board;
	private BoardGUI boardGUI;
	private JPanel namePanel;
	private JPanel nextPlayerButton;
	private JPanel accusationButton;
	private JPanel dieRollPanel;
	private JPanel guessPanel;
	private JPanel guessResultPanel;
	
	public ControlGUI(BoardGUI boardGUI) {
		this.boardGUI = boardGUI;
		board = Board.getInstance();
		// Create a layout with 2 rows and 3 columns
		setLayout(new GridLayout(2,0));
		namePanel = new NamePanel("Whose Turn?"); // Display of whose turn it is.
		add(namePanel);
		nextPlayerButton = createButtonPanel("Next Player", new ButtonListener()); // Button to move to the next player.
		add(nextPlayerButton);
		accusationButton = createButtonPanel("Make an Accusation", null); // Button to make an accusation.
		add(accusationButton);
		dieRollPanel = new EtchedPanel("Die", "Roll", Integer.toString(board.getDiceRoll()), false, HEIGHT/3, WIDTH/8); // Display the roll of the die.
		add(dieRollPanel);
		guessPanel = new EtchedPanel("Guess", "Guess", "", true, HEIGHT/3, WIDTH/3); // Display the guesses made by other players.
		add(guessPanel);
		guessResultPanel = new EtchedPanel("Guess Result", "Response", "", false, HEIGHT/3, WIDTH/5); // Display the guess result.
		add(guessResultPanel);
	}
	
	// Creates a panel with two rows filled with a label and a text field.
	private class NamePanel extends JPanel {
		JTextField name;
	
		public NamePanel(String label) {
			// Create an outer panel to house a different inner panel.
			// This is designed so that we can change the size of elements while still using a grid layout.
			JPanel panel = new JPanel(new BorderLayout());
			// Use a grid layout, 2 rows, 2 column (label, text)
			panel.setLayout(new GridLayout(2,1)); // Two rows, one column
		 	JLabel nameLabel = new JLabel(label);
		 	nameLabel.setHorizontalAlignment(JLabel.CENTER); // Center the label.
		 	name = new JTextField(board.currentPlayer().getName());
			name.setEditable(false); // Disable editing of the text field.
			panel.add(nameLabel);
			// panel.setPreferredSize(new Dimension(10, 10)); // Set Dimensions if desired.
			panel.add(name);
			add(panel);
		}
		
		public void resetTextField(String text) {
			name.setText(text);
		}
	}
	
	// Private class for our etched panels. Could have copied the code three times, but this seemed like the most efficient way of doing it.
	private class EtchedPanel extends JPanel {
		JTextField name;
		
		// Create a new etched border with a name, containing a label and text field.
		public EtchedPanel(String outerLabel, String innerLabel, String contents, boolean verticleGridLayout, int height, int width) {
			// Create an outer panel to house a different inner panel.
			// This is designed so that we can change the size of elements while still using a grid layout.	
			JPanel panel = new JPanel(new BorderLayout());
		 	// Use a grid layout, 1 row, 2 elements (label, text)
		 	if (verticleGridLayout) { // If verticleGridLayout is true, there will be two rows, one column. 
		 		panel.setLayout(new GridLayout(2, 1));
		 	} else { // Otherwise, one column and two rows.
		 		panel.setLayout(new GridLayout(1, 2));
		 	}
		 	JLabel nameLabel = new JLabel(innerLabel); // Set the name of the inner label.
		 	name = new JTextField(contents);
			name.setEditable(false); // Disable editing of the text field.
			panel.setPreferredSize(new Dimension(width, height)); // Set Dimensions if desired.
			panel.add(nameLabel);
			panel.add(name);
			panel.setBorder(new TitledBorder (new EtchedBorder(), outerLabel)); // Set the etched border with a title.
			add(panel);
		}

		public void resetTextField(String text) {
			name.setText(text);
		}
	}

	
	 
	private JPanel createButtonPanel(String name, ActionListener listener) {
		// Create a new button, add to a new panel, and return.
		JButton button = new JButton(name);
		button.addActionListener(new ButtonListener());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(button);
		return panel;
	}
	
	// Called from the button listener--updates all fields without destroying the panels.
	private void resetPanels() {
		((EtchedPanel)dieRollPanel).resetTextField(Integer.toString(board.getDiceRoll()));
		((EtchedPanel)guessPanel).resetTextField("");
		((EtchedPanel)guessResultPanel).resetTextField("");
		((NamePanel)namePanel).resetTextField(board.currentPlayer().getName());
	}
	
	private void updateGuessPanels(Solution suggestion) {
		String guessResult = board.initiateSuggestion(suggestion).getName();
		((EtchedPanel)guessPanel).resetTextField(suggestion.toString());
		((EtchedPanel)guessResultPanel).resetTextField(guessResult);
		
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
	        	if (board.currentPlayer() instanceof HumanPlayer && ((HumanPlayer)board.currentPlayer()).getPlayerMovementStatus()) {
	        		SwingUtilities.invokeLater(new Runnable() { // If there is any delay in the logic, this will prevent the GUI from crashing as a result.
	                    @Override
	                    public void run() {
                        	board.nextTurn(); // Move on to the next player's turn.
        					resetPanels(); // Reset all panels in the control GUI.
        					boardGUI.repaint(); // Repaint the board GUI.
        					boardGUI.revalidate(); // Repaint the board GUI.
	                    }
	                });
				} else if (board.currentPlayer() instanceof ComputerPlayer) {
					SwingUtilities.invokeLater(new Runnable() {
	                    @Override
	                    public void run() {
                        	board.nextTurn(); // Move on to the next player's turn.
        					resetPanels(); // Reset all panels in the control GUI.
        					boardGUI.repaint(); // Repaint the board GUI.
        					boardGUI.revalidate(); // Repaint the board GUI.
        					// If the computer player has entered a room, then update the suggestion and make a suggestion.
        					if (board.getCellAt(board.currentPlayer().getRow(), board.currentPlayer().getCol()).isDoorway()) {
        						updateGuessPanels(((ComputerPlayer)board.currentPlayer()).createSuggestion(board.getPlayers(), board.getCellAt(board.currentPlayer().getRow(), board.currentPlayer().getCol()), board.getWeapons()));
        						
        					}
	                    }
	                });
				}
			}
			
		}
	

	

}
