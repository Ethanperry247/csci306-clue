// Authors: Ethan Perry and Caleb Pan

package GUI;


import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotesDialog extends JDialog {

	public DetectiveNotesDialog() {
		setTitle("Detective Notes");
		setSize(450, 600);
		setLayout(new GridLayout(3,2)); // Three rows, two columns.
		
		String[] personArray = {"Mr. Green", "Mrs. White", "Mrs. Peacock", "Ms. Scarlett", "Col. Mustard", "Prof. Plum"};
		ArrayList<String> personNames = new ArrayList<String>(Arrays.asList(personArray)); // Create an array list with the names of the players.
		JPanel people = createCheckBoxPanel(personNames, "People");
		add(people);
		JPanel personGuess = createComboBoxPanel(personNames, "Person Guess");
		add(personGuess);
		
		String[] roomArray = {"Lounge", "Dungeon", "Greenhouse"};
		ArrayList<String> roomNames = new ArrayList<String>(Arrays.asList(roomArray)); // Create an array list with the names of the rooms.
		JPanel rooms = createCheckBoxPanel(roomNames, "Rooms");
		add(rooms);
		JPanel roomGuess = createComboBoxPanel(roomNames, "Room Guess");
		add(roomGuess);
		
		String[] weaponArray = {"Dagger", "Revolver", "Candlestick"};
		ArrayList<String> weaponNames = new ArrayList<String>(Arrays.asList(weaponArray)); // Create an array list with the names of the rooms.
		JPanel weapons = createCheckBoxPanel(weaponNames, "Weapons");
		add(weapons);
		JPanel weaponGuess = createComboBoxPanel(weaponNames, "Weapon Guess");
		add(weaponGuess);
	}
	
	// Return a combo box panel with the desired items and an etched border.
	public JPanel createComboBoxPanel(ArrayList<String> items, String title) {
		JComboBox<String> comboBox = new JComboBox<String>(); // Create a new combo box.
		JPanel comboBoxPanel = new JPanel(new GridLayout()); // Create a panel to return.
		for (String item: items) { // Loop through the passed in items and add them to the comboBox.
			comboBox.addItem(item);
		}
		comboBoxPanel.add(comboBox); // Add the combo box to the panel.
		comboBoxPanel.setBorder(new TitledBorder (new EtchedBorder(), title)); // Set the etched title border.
		return comboBoxPanel;
	}
	
	public JPanel createCheckBoxPanel(ArrayList<String> items, String title) {
		JPanel checkBoxPanel = new JPanel(new GridLayout(0,2)); // Created a new JPanel with unlimited rows and two columns.
		for (String item: items) { // Add a check box for each item passed in in the array list.
			checkBoxPanel.add(new JCheckBox(item));
		}
		
		checkBoxPanel.setBorder(new TitledBorder (new EtchedBorder(), title)); // Set the etched title border.
		return checkBoxPanel;
	}
	

}
