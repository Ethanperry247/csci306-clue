// Authors: Ethan Perry and Caleb Pan

package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;

public class MyCardsGUI extends JPanel {
	private Board board;

	public MyCardsGUI() {
		// Get the instance of the board.
		board = Board.getInstance();
		// Obtain the information about the current player.
		Player player = board.currentPlayer();
		// Get the cards of the current player.
		ArrayList<Card> cards = player.getCards();
		
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(120,600));
		
		// Three different arraylists for each kind of card.
		ArrayList<String> people = new ArrayList<String>();
		ArrayList<String> rooms = new ArrayList<String>();
		ArrayList<String> weapons = new ArrayList<String>();
		
		// Loop through the current players cards and put them into their respective lists.
		for (Card card: cards) {
			if (card.getType() == CardType.PERSON) {
				people.add(card.getName());
			} else if (card.getType() == CardType.ROOM) {
				rooms.add(card.getName());
			} else {
				weapons.add(card.getName());
			}
		}
		
		// Create three jpanels to contain the current player's cards.
		JPanel personSlot = createCardSlot("People", people);
		JPanel roomSlot = createCardSlot("Rooms", rooms);
		JPanel weaponSlot = createCardSlot("Weapons", weapons);
		
		// Add those jpanels to the main panel.
		add(personSlot);
		add(roomSlot);
		add(weaponSlot);
		
		setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
	}
	
	public JPanel createCardSlot(String title, ArrayList<String> cards) {
		JPanel panel = new JPanel(new GridLayout(1,0));
		panel.setPreferredSize(new Dimension(100,120));
		
		// Loop through the given names and add them to the panel.
		for (String cardName: cards) {
			JTextField card = new JTextField(cardName);
			panel.add(card);
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), title));
		return panel;
	}

}
