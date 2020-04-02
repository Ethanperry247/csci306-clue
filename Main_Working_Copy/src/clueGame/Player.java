// Authors: Caleb Pan, Ethan Perry

package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Player {
	
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> playerCards;	// note: each player has 3 cards
	private ArrayList<Card> weaponsSeen;	// The weapons observed from disproved suggestions.
	private ArrayList<Card> roomsSeen;		// The rooms observed from disproved suggestions.
	private ArrayList<Card> playersSeen;	// The players observed from disproved suggestions.
	
	public Player(String playerName, int row, int column, String color) {
		this.row = row;
		this.column = column;
		this.playerName = playerName;
		this.color = convertColor(color); // The colors passed in must be in all caps (i.e. RED, GREEN, etc.).
		this.playerCards = new ArrayList<Card>();
		this.weaponsSeen = new ArrayList<Card>();
		this.roomsSeen = new ArrayList<Card>();
		this.playersSeen = new ArrayList<Card>();
	}
	
	public String getName() {
		return playerName;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return column;
	}
	
	public ArrayList<Card> getCards() {
		return playerCards;
	}
	
	// from CluePlayer.pdf
	// Be sure to trim the color, we don't want spaces around the name
	public Color convertColor(String strColor) {
		Color color; 
		try {     // We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null); 
		} catch (Exception e) {  
			color = null; // Not defined  
		}
		return color;
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		
		List<Card> matchingCards = new ArrayList<Card>();
		
		// If the suggestion contains any of the same cards as contained in the players hand,
		// return that card. Otherwise return a dummy card called 'x.'
		for (Card card: playerCards) {
			if (card.getName().equals(suggestion.getPerson()) ||
				card.getName().equals(suggestion.getWeapon()) ||
				card.getName().equals(suggestion.getRoom())) {
				matchingCards.add(card);
			}
		}
		
		if (matchingCards.size() == 0) { // If no cards are matching, return zero.
			return null;
		} else if (matchingCards.size() == 1) { // If one card matches, return that card.
			return matchingCards.get(0);
		} else { // Otherwise, if multiple match it, return a random selection.
			return matchingCards.get((int)Math.random()%matchingCards.size());
		}
	}
	
}
