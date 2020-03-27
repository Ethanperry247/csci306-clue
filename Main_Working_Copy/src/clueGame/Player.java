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
	private List<Card> playerCards;	// note: each player has 3 cards
	
	
	public Player(String playerName, int row, int column, Color color) {
		this.row = row;
		this.column = column;
		this.playerName = playerName;
		this.color = color;
		this.playerCards = new ArrayList<Card>();
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
	
	// to be implemented
	public Card disproveSuggestion(Solution suggestion) {
		Card x = new Card("x", CardType.PERSON);
		return x;
	}
	
}
