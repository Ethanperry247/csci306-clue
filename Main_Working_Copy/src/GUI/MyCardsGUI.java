package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MyCardsGUI extends JPanel {

	public MyCardsGUI() {
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(120,600));
		
		JPanel people = createCardSlot("People", "Mr. Green");
		JPanel rooms = createCardSlot("Rooms", "Dining Room");
		JPanel weapons = createCardSlot("Weapons", "Wrench");
		add(people);
		add(rooms);
		add(weapons);
		
		setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
	}
	
	public JPanel createCardSlot(String title, String cardName) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(100,120));
		JTextField card = new JTextField(cardName);
		panel.add(card);
		panel.setBorder(new TitledBorder (new EtchedBorder(), title));
		return panel;
	}

}
