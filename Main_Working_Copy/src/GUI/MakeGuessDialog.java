package GUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Player;
import clueGame.Solution;

public class MakeGuessDialog extends JDialog {
	private Board board;
	private Solution suggestion;
	private String room;
	private String person;
	private String weapon;
	private JButton submitButton;
	private JButton cancelButton;
	private MakeGuessDialog self = this;
	private ControlGUI controlGUI;
	private JComboBox<String> weaponComboBox;
	private JComboBox<String> personComboBox;

	public MakeGuessDialog(ControlGUI controlGUI) {
		this.controlGUI = controlGUI;
		board = Board.getInstance();
		Player player = board.currentPlayer();
		setTitle("Make a Guess");
		setSize(400, 300);
		setLayout(new GridLayout(4,2)); // Three rows, two columns.
		
		JLabel roomLabel = new JLabel("Your Room: ");
		add(roomLabel);
		
		room = board.getLegend().get(board.getCellAt(player.getRow(), player.getCol()).getInitial()).get(0);		
		JTextField roomGuess = new JTextField(room);						
		roomGuess.setEditable(false);
		add(roomGuess);
		
		JLabel personLabel = new JLabel("Person: ");
		add(personLabel);
		
		ArrayList<String> players = new ArrayList<String>(board.getAllPlayerNames());
		JPanel personGuess = createPersonComboBoxPanel(players);
		add(personGuess);
		person = players.get(0);
		
		JLabel weaponLabel = new JLabel("Weapon: ");
		add(weaponLabel);
		
		ArrayList<String> weapons = new ArrayList<String>(board.getWeaponNames());
		JPanel weaponGuess = createWeaponComboBoxPanel(weapons);
		add(weaponGuess);
		weapon = weapons.get(0);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ButtonListener());
		add(submitButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ButtonListener());
		add(cancelButton);
		
	}
	
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == submitButton) {
				controlGUI.updateGuessPanels(new Solution(person, room, weapon));
				self.setVisible(false);
			} else {
				self.setVisible(false);
			}
		}
	}
	
	// Return a combo box panel with the desired items
	public JPanel createWeaponComboBoxPanel(ArrayList<String> items) {
		weaponComboBox = new JComboBox<String>(); // Create a new combo box.
		JPanel comboBoxPanel = new JPanel(new GridLayout()); // Create a panel to return.
		comboBoxPanel.setLayout(new BorderLayout());
		for (String item: items) { // Loop through the passed in items and add them to the comboBox.
			weaponComboBox.addItem(item);
		}
		weaponComboBox.addActionListener(new ComboBoxListener());
		comboBoxPanel.add(weaponComboBox); // Add the combo box to the panel.
		return comboBoxPanel;
	}
	
	// Return a combo box panel with the desired items 
		public JPanel createPersonComboBoxPanel(ArrayList<String> items) {
			personComboBox = new JComboBox<String>(); // Create a new combo box.
			JPanel comboBoxPanel = new JPanel(new GridLayout()); // Create a panel to return.
			comboBoxPanel.setLayout(new BorderLayout());
			for (String item: items) { // Loop through the passed in items and add them to the comboBox.
				personComboBox.addItem(item);
			}
			personComboBox.addActionListener(new ComboBoxListener());
			comboBoxPanel.add(personComboBox); // Add the combo box to the panel.
			return comboBoxPanel;
		}
	
	private class ComboBoxListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == weaponComboBox) {
				weapon = weaponComboBox.getSelectedItem().toString();
			} else {
				person = personComboBox.getSelectedItem().toString();
			}
		}
		
	}

}
